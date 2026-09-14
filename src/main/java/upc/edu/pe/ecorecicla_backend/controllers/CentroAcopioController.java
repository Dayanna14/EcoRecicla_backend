package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.CentroAcopioDTOInsert;
import upc.edu.pe.ecorecicla_backend.dtos.CentroAcopioDTOList;
import upc.edu.pe.ecorecicla_backend.entities.CentroAcopio;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ICentroAcopioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/centrosacopio")
public class CentroAcopioController {
    private final ModelMapper modelMapper;
    private final ICentroAcopioService cS;

    public CentroAcopioController(
            ModelMapper modelMapper,
            ICentroAcopioService cS) {
        this.modelMapper = modelMapper;
        this.cS = cS;
    }

    @GetMapping
    public ResponseEntity<List<CentroAcopioDTOList>> listar() {

        List<CentroAcopioDTOList> lista = cS.list()
                .stream()
                .map(c -> modelMapper.map(c, CentroAcopioDTOList.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<CentroAcopioDTOInsert> registrar(
            @Valid @RequestBody CentroAcopioDTOInsert dto) {

        CentroAcopio centro =
                modelMapper.map(dto, CentroAcopio.class);

        cS.insert(centro);

        CentroAcopioDTOInsert responseDTO =
                modelMapper.map(centro, CentroAcopioDTOInsert.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(centro.getIdCentro())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroAcopioDTOInsert> buscarPorId(
            @PathVariable Long id) {

        CentroAcopio centro = cS.searchId(id);

        CentroAcopioDTOInsert dto =
                modelMapper.map(centro, CentroAcopioDTOInsert.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        cS.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<CentroAcopioDTOInsert> actualizar(
            @Valid @RequestBody CentroAcopioDTOInsert dto) {

        CentroAcopio centro =
                modelMapper.map(dto, CentroAcopio.class);

        cS.update(centro);

        CentroAcopioDTOInsert responseDTO =
                modelMapper.map(centro, CentroAcopioDTOInsert.class);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CentroAcopioDTOList>>
    buscarPorEstado(@RequestParam Boolean estado) {

        List<CentroAcopioDTOList> lista =
                cS.buscarPorEstado(estado)
                        .stream()
                        .map(c -> modelMapper.map(
                                c,
                                CentroAcopioDTOList.class))
                        .toList();

        return ResponseEntity.ok(lista);
    }
}


