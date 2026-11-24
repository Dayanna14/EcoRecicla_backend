package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upc.edu.pe.ecorecicla_backend.dtos.LoginResponseDTO;
import upc.edu.pe.ecorecicla_backend.dtos.RegistroDTO;
import upc.edu.pe.ecorecicla_backend.entities.Rol;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.securities.JwtTokenService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRolService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IUsuarioService;

import java.util.List;

// Público (ver SecurityConfig): una persona nueva crea su cuenta desde la web
@RestController
@RequestMapping("/registro")
public class RegistroController {
    private final IUsuarioService uS;
    private final IRolService rS;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    public RegistroController(IUsuarioService uS, IRolService rS, PasswordEncoder passwordEncoder,
                              UserDetailsService userDetailsService, JwtTokenService jwtTokenService) {
        this.uS = uS;
        this.rS = rS;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> registrar(@Valid @RequestBody RegistroDTO dto) {

        if (uS.existeNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ese nombre de usuario ya está en uso.");
        }
        if (uS.existeEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ese email ya está registrado.");
        }

        // El rol siempre lo pone el servidor: nadie puede registrarse como admin
        Rol rol = rS.buscarPorNombreExacto("ROLE_RECICLADOR");

        Usuarios usuario = new Usuarios();
        usuario.setNombre(dto.getNombre().trim());
        usuario.setEmail(dto.getEmail().trim().toLowerCase());
        usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        usuario.setRol(rol);
        usuario.setEnabled(true);
        usuario.setDebeCambiarContrasenia(false);

        uS.insert(usuario);

        // Inicia sesión automáticamente: se devuelve el mismo formato que /login
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getNombre());
        String token = jwtTokenService.generateToken(userDetails);

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                userDetails.getUsername(),
                List.of(rol.getNombre()),
                usuario.getId_usuario(),
                usuario.getNombre(),
                false
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
