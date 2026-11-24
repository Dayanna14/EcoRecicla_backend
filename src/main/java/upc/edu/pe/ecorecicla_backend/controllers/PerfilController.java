package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecorecicla_backend.dtos.CambiarContraseniaDTO;
import upc.edu.pe.ecorecicla_backend.dtos.PerfilDTO;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IUsuarioService;

// "Mi perfil": cada usuario solo ve y cambia SUS datos (se identifica con el token)
@RestController
@RequestMapping("/api/perfil")
@PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
public class PerfilController {
    private final IUsuarioService uS;
    private final PasswordEncoder passwordEncoder;

    public PerfilController(IUsuarioService uS, PasswordEncoder passwordEncoder) {
        this.uS = uS;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<PerfilDTO> miPerfil(Authentication authentication) {
        Usuarios usuario = usuarioActual(authentication);
        return ResponseEntity.ok(aPerfil(usuario));
    }

    @PutMapping("/contrasenia")
    public ResponseEntity<PerfilDTO> cambiarContrasenia(
            @Valid @RequestBody CambiarContraseniaDTO dto,
            Authentication authentication) {

        Usuarios usuario = usuarioActual(authentication);

        if (!passwordEncoder.matches(dto.getContraseniaActual(), usuario.getContrasenia())) {
            throw new IllegalArgumentException("La contraseña actual no es correcta.");
        }

        usuario.setContrasenia(passwordEncoder.encode(dto.getContraseniaNueva()));
        // Si tenía una contraseña temporal, ya no se le vuelve a pedir el cambio
        usuario.setDebeCambiarContrasenia(false);
        uS.update(usuario);

        return ResponseEntity.ok(aPerfil(usuario));
    }

    private Usuarios usuarioActual(Authentication authentication) {
        return uS.buscarPorEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario de la sesión."));
    }

    private PerfilDTO aPerfil(Usuarios usuario) {
        PerfilDTO dto = new PerfilDTO();
        dto.setIdUsuario(usuario.getId_usuario());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol() != null ? usuario.getRol().getNombre() : null);
        dto.setDebeCambiarContrasenia(Boolean.TRUE.equals(usuario.getDebeCambiarContrasenia()));
        return dto;
    }
}
