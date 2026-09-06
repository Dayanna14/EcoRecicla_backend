package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.RolDTOInsert;
import upc.edu.pe.ecorecicla_backend.dtos.RolDTOList;
import upc.edu.pe.ecorecicla_backend.entities.Rol;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRolService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    private final ModelMapper modelMapper;
    private final IRolService rS;

    public RolController(ModelMapper modelMapper, IRolService rS) {
        this.modelMapper = modelMapper;
        this.rS = rS;
    }
    @GetMapping

    public ResponseEntity<List<RolDTOList>> listar() {
        List<RolDTOList> lista = rS.list()
                .stream()
                .map(rol -> modelMapper.map(rol, RolDTOList.class))
                .toList();
        return ResponseEntity.ok(lista);
    }
    @PostMapping

    public ResponseEntity<RolDTOInsert> registrar(
            @Valid @RequestBody RolDTOInsert dto) {

        Rol rol = modelMapper.map(dto, Rol.class);

        rS.insert(rol);

        RolDTOInsert responseDTO =
                modelMapper.map(rol, RolDTOInsert.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rol.getIdRol())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RolDTOInsert> buscarPorId(
            @PathVariable Long id) {

        Rol rol = rS.searchId(id);

        RolDTOInsert dto =
                modelMapper.map(rol, RolDTOInsert.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        rS.delete(id);

        return ResponseEntity.noContent().build();
    }
    @PutMapping
    public ResponseEntity<RolDTOInsert> actualizar(
            @Valid @RequestBody RolDTOInsert dto) {

        Rol rol = modelMapper.map(dto, Rol.class);

        rS.update(rol);

        RolDTOInsert responseDTO =
                modelMapper.map(rol, RolDTOInsert.class);

        return ResponseEntity.ok(responseDTO);
    }
}
