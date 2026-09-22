package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.CanjeDTOInsert;
import upc.edu.pe.ecorecicla_backend.dtos.CanjeDTOList;
import upc.edu.pe.ecorecicla_backend.entities.Canje;
import upc.edu.pe.ecorecicla_backend.entities.Recompensa;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ICanjeService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/canjes")
public class CanjeController {
    private final ICanjeService cS;
    private final ModelMapper modelMapper;

    public CanjeController(ICanjeService cS, ModelMapper modelMapper) {
        this.cS = cS;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','RECICLADOR')")
    @GetMapping
    public ResponseEntity<List<CanjeDTOList>> list() {
        List<CanjeDTOList> listDTO = cS.list().stream()
                .map(canje -> modelMapper.map(canje, CanjeDTOList.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDTO);
    }

    @PreAuthorize("hasRole('RECICLADOR')")
    @PostMapping
    public ResponseEntity<CanjeDTOInsert> insert(@Valid @RequestBody CanjeDTOInsert dto) {
        Canje canje = modelMapper.map(dto, Canje.class);

        if (canje.getFecha() == null) {
            canje.setFecha(LocalDateTime.now());
        }

        Recompensa recompensa = new Recompensa();
        recompensa.setIdRecompensa(dto.getIdRecompensa());
        canje.setRecompensa(recompensa);

        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(dto.getIdUsuario());
        canje.setUsuario(usuario);

        cS.insert(canje);

        CanjeDTOInsert responseDTO = modelMapper.map(canje, CanjeDTOInsert.class);
        responseDTO.setIdRecompensa(dto.getIdRecompensa());
        responseDTO.setIdUsuario(dto.getIdUsuario());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(canje.getIdCanje())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanjeDTOList> listId(@PathVariable("id") Long id) {
        Canje canje = cS.listId(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró el canje con el id: " + id)
        );
        CanjeDTOList dto = modelMapper.map(canje, CanjeDTOList.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        cS.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<CanjeDTOInsert> update(@Valid @RequestBody CanjeDTOInsert dto) {
        if (dto.getIdCanje() == null) {
            throw new IllegalArgumentException("El idCanje no puede ser nulo para actualizar.");
        }

        Canje canje = modelMapper.map(dto, Canje.class);

        Recompensa recompensa = new Recompensa();
        recompensa.setIdRecompensa(dto.getIdRecompensa());
        canje.setRecompensa(recompensa);

        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(dto.getIdUsuario());
        canje.setUsuario(usuario);

        cS.update(canje);

        CanjeDTOInsert responseDTO = modelMapper.map(canje, CanjeDTOInsert.class);
        responseDTO.setIdRecompensa(dto.getIdRecompensa());
        responseDTO.setIdUsuario(dto.getIdUsuario());

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CanjeDTOList>> buscarPorEstado(@RequestParam String estado) {
        List<CanjeDTOList> lista = cS.buscarPorEstado(estado).stream()
                .map(canje -> modelMapper.map(canje, CanjeDTOList.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
