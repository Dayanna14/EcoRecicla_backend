package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.UsuarioInsertDTO;
import upc.edu.pe.ecorecicla_backend.dtos.UsuarioListDTO;
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
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<UsuarioListDTO> registrar(
            @Valid @RequestBody UsuarioInsertDTO dto) {

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
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
    @GetMapping
    public ResponseEntity<List<UsuarioListDTO>> listar() {

        List<UsuarioListDTO> lista = uS.list()
                .stream()
                .map(u -> modelMapper.map(u, UsuarioListDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
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

    @PreAuthorize("hasRole('ADMINISTRADOR')")
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

    @PreAuthorize("hasRole('ADMINISTRADOR')")
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
        usuario.setContrasenia(dto.getContrasenia());
        usuario.setRol(rol);

        uS.update(usuario);

        UsuarioListDTO responseDTO =
                modelMapper.map(usuario, UsuarioListDTO.class);

        return ResponseEntity.ok(responseDTO);
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
    @GetMapping("/queries/usuarios-por-rol")
    public ResponseEntity<List<Object[]>> usuariosPorRol() {
        return ResponseEntity.ok(uS.usuariosPorRol());
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
    @GetMapping("/usuarios-por-estado")
    public ResponseEntity<List<Object[]>> usuariosPorEstado() {
        return ResponseEntity.ok(uS.usuariosPorEstado());
    }

}
