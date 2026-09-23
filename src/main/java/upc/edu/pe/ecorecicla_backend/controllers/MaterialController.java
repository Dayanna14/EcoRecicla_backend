package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.MaterialDTOInsert;
import upc.edu.pe.ecorecicla_backend.dtos.MaterialDTOList;
import upc.edu.pe.ecorecicla_backend.entities.Material;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IMaterialService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    private final IMaterialService mS;
    private final ModelMapper modelMapper;

    public MaterialController(IMaterialService mS, ModelMapper modelMapper) {
        this.mS = mS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MaterialDTOList>> list() {
        List<MaterialDTOList> listDTO = mS.list().stream()
                .map(material -> modelMapper.map(material, MaterialDTOList.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTOList> listId(@PathVariable Long id) {
        Material material = mS.listId(id);
        if (material == null) {
            return ResponseEntity.notFound().build();
        }
        MaterialDTOList dto = modelMapper.map(material, MaterialDTOList.class);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<MaterialDTOInsert> insert(@Valid @RequestBody MaterialDTOInsert dto) {
        Material material = modelMapper.map(dto, Material.class);

        mS.insert(material);

        MaterialDTOInsert responseDTO = modelMapper.map(material, MaterialDTOInsert.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(material.getIdMaterial())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTOInsert> update(@PathVariable Long id,
                                                    @Valid @RequestBody MaterialDTOInsert dto) {
        Material material = modelMapper.map(dto, Material.class);
        material.setIdMaterial(id);

        mS.update(material);

        MaterialDTOInsert responseDTO = modelMapper.map(material, MaterialDTOInsert.class);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mS.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MaterialDTOList>> buscarPorNombre(@RequestParam String nombre) {
        List<MaterialDTOList> resultado = mS.buscarPorNombre(nombre).stream()
                .map(material -> modelMapper.map(material, MaterialDTOList.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }
}