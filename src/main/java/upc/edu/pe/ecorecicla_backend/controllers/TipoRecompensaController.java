package upc.edu.pe.ecorecicla_backend.controllers;


import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.TipoRecompensaDTOInsert;
import upc.edu.pe.ecorecicla_backend.dtos.TipoRecompensaDTOList;
import upc.edu.pe.ecorecicla_backend.entities.TipoRecompensa;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ITipoRecompensaService;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tiporecompensas")
public class TipoRecompensaController {
    private final ITipoRecompensaService tS;
    private final ModelMapper modelMapper;

    public TipoRecompensaController(ITipoRecompensaService tS, ModelMapper modelMapper) {
        this.tS = tS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TipoRecompensaDTOList>> list() {
        List<TipoRecompensaDTOList> listDTO = tS.list().stream()
                .map(tipo -> modelMapper.map(tipo, TipoRecompensaDTOList.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoRecompensaDTOList> listId(@PathVariable Long id) {
        TipoRecompensa tipo = tS.listId(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(tipo, TipoRecompensaDTOList.class));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<TipoRecompensaDTOInsert> insert(@Valid @RequestBody TipoRecompensaDTOInsert dto) {
        TipoRecompensa tipo = modelMapper.map(dto, TipoRecompensa.class);
        tS.insert(tipo);

        TipoRecompensaDTOInsert responseDTO = modelMapper.map(tipo, TipoRecompensaDTOInsert.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tipo.getIdTipoRecompensa())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoRecompensaDTOList> update(@PathVariable Long id, @Valid @RequestBody TipoRecompensaDTOInsert dto) {
        TipoRecompensa existente = tS.listId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        TipoRecompensa tipo = modelMapper.map(dto, TipoRecompensa.class);
        tipo.setIdTipoRecompensa(id);
        tS.update(tipo);

        return ResponseEntity.ok(modelMapper.map(tipo, TipoRecompensaDTOList.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        TipoRecompensa existente = tS.listId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        tS.delete(id);
        return ResponseEntity.ok("Tipo de recompensa eliminado correctamente");
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TipoRecompensaDTOList>> buscarPorNombre(@RequestParam String nombre) {
        List<TipoRecompensaDTOList> resultado = tS.buscarPorNombre(nombre).stream()
                .map(tipo -> modelMapper.map(tipo, TipoRecompensaDTOList.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/recompensas/estado")
    public ResponseEntity<List<Map<String, Object>>> buscarRecompensaPorEstado(@RequestParam Boolean estado) {
        List<Object[]> resultado = tS.buscarRecompensaPorEstado(estado);

        List<Map<String, Object>> respuesta = resultado.stream()
                .map(fila -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("idRecompensa", fila[0]);
                    item.put("idTipo", fila[1]);
                    item.put("nombre", fila[2]);
                    item.put("costoPuntos", fila[3]);
                    item.put("estado", fila[4]);
                    item.put("imagenUrl", fila[5]);
                    return item;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<Map<String, Object>>> tipoRecompensaMasUsado() {
        List<Object[]> resultado = tS.tipoRecompensaMasUsado();

        List<Map<String, Object>> respuesta = resultado.stream()
                .map(fila -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("nombreTipoRecompensa", fila[0]);
                    item.put("cantidadCanjes", fila[1]);
                    item.put("totalPuntosCanjeados", fila[2]);
                    return item;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }
}
