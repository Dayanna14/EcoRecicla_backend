package upc.edu.pe.ecorecicla_backend.controllers;

import upc.edu.pe.ecorecicla_backend.dtos.ContraseniaTemporalDTO;
import java.security.SecureRandom;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.RolMasUtilizadoDTO;
import upc.edu.pe.ecorecicla_backend.dtos.UsuarioInsertDTO;
import upc.edu.pe.ecorecicla_backend.dtos.UsuarioListDTO;
import upc.edu.pe.ecorecicla_backend.dtos.UsuarioPorEstadoDTO;
import upc.edu.pe.ecorecicla_backend.entities.Rol;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRolService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IUsuarioService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class UsuariosController {
    private final IUsuarioService uS;
    private final ModelMapper modelMapper;
    private final IRolService rS;
    private final PasswordEncoder passwordEncoder;

    public UsuariosController(IUsuarioService uS, ModelMapper modelMapper, IRolService rS, PasswordEncoder passwordEncoder) {
        this.uS = uS;
        this.modelMapper = modelMapper;
        this.rS = rS;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<UsuarioListDTO> registrar(
            @Valid @RequestBody UsuarioInsertDTO dto) {

        if (dto.getContrasenia() == null || dto.getContrasenia().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        Rol rol = rS.searchId(dto.getIdRol());

        Usuarios usuario = modelMapper.map(dto, Usuarios.class);

        usuario.setRol(rol);

        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));

        uS.insert(usuario);

        UsuarioListDTO responseDTO =
                modelMapper.map(usuario, UsuarioListDTO.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId_usuario())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioListDTO>> listar() {

        List<UsuarioListDTO> lista = uS.list()
                .stream()
                .map(u -> modelMapper.map(u, UsuarioListDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioListDTO> buscarId(
            @PathVariable Long id) {

        Usuarios usuario = uS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El usuario no existe."
                        ));

        UsuarioListDTO responseDTO =
                modelMapper.map(usuario, UsuarioListDTO.class);

        return ResponseEntity.ok(responseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        Usuarios usuario = uS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El usuario no existe."
                        ));

        uS.delete(usuario.getId_usuario());

        return ResponseEntity.noContent().build();
    }


    @PutMapping
    public ResponseEntity<UsuarioListDTO> actualizar(
            @Valid @RequestBody UsuarioInsertDTO dto) {

        Optional<Usuarios> existente =
                uS.listId(dto.getId_usuario());

        if (existente.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No existe un usuario con el id: "
                            + dto.getId_usuario()
            );
        }

        Rol rol = rS.searchId(dto.getIdRol());

        Usuarios usuario = existente.get();

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        // Si el admin no escribe una nueva contraseña, se conserva la actual
        if (dto.getContrasenia() != null && !dto.getContrasenia().isBlank()) {
            usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        }
        usuario.setRol(rol);

        uS.update(usuario);

        UsuarioListDTO responseDTO =
                modelMapper.map(usuario, UsuarioListDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/queries/usuarios-por-rol")
    public ResponseEntity<List<RolMasUtilizadoDTO>> usuariosPorRol() {
        List<RolMasUtilizadoDTO> lista = uS.usuariosPorRol()
                .stream()
                .map(item -> {
                    RolMasUtilizadoDTO dto = new RolMasUtilizadoDTO();
                    dto.setNombreRol((String) item[0]);
                    dto.setCantidadUsuarios(((Number) item[1]).intValue());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuarios-por-estado")
    public ResponseEntity<List<UsuarioPorEstadoDTO>> usuariosPorEstado() {
        List<UsuarioPorEstadoDTO> lista = uS.usuariosPorEstado()
                .stream()
                .map(item -> {
                    UsuarioPorEstadoDTO dto = new UsuarioPorEstadoDTO();
                    dto.setEstado((String) item[0]);
                    dto.setCantidadUsuarios(((Number) item[1]).intValue());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(lista);
    }

    // Opción B de "olvidé mi contraseña": el admin genera una contraseña temporal,
    // se la entrega al usuario y este debe cambiarla al ingresar
    @PutMapping("/{id}/restablecer")
    public ResponseEntity<ContraseniaTemporalDTO> restablecerContrasenia(
            @PathVariable Long id) {

        Usuarios usuario = uS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El usuario no existe."
                        ));

        String temporal = "Eco-" + (1000 + new SecureRandom().nextInt(9000));

        usuario.setContrasenia(passwordEncoder.encode(temporal));
        usuario.setDebeCambiarContrasenia(true);
        uS.update(usuario);

        ContraseniaTemporalDTO dto = new ContraseniaTemporalDTO();
        dto.setNombre(usuario.getNombre());
        dto.setContraseniaTemporal(temporal);

        return ResponseEntity.ok(dto);
    }

    // Habilitar o inhabilitar una cuenta (una cuenta inhabilitada no puede iniciar sesión)
    @PutMapping("/{id}/estado")
    public ResponseEntity<UsuarioListDTO> cambiarEstado(
            @PathVariable Long id) {

        Usuarios usuario = uS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El usuario no existe."
                        ));

        usuario.setEnabled(!Boolean.TRUE.equals(usuario.getEnabled()));
        uS.update(usuario);

        return ResponseEntity.ok(modelMapper.map(usuario, UsuarioListDTO.class));
    }

}
