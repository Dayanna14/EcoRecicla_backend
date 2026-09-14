package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaDTOInsert;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaDTOList;
import upc.edu.pe.ecorecicla_backend.entities.CentroAcopio;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;
import upc.edu.pe.ecorecicla_backend.entities.Material;
import upc.edu.pe.ecorecicla_backend.entities.Usuario;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IEntregaService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entregas")
public class EntregaController {


    private final IEntregaService eS;
    private final ModelMapper modelMapper;

    public EntregaController(IEntregaService eS, ModelMapper modelMapper) {
        this.eS = eS;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public ResponseEntity<List<EntregaDTOList>> list() {
        List<EntregaDTOList> listDTO = eS.list().stream()
                .map(entrega -> modelMapper.map(entrega, EntregaDTOList.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listDTO);
    }

    @PostMapping
    public ResponseEntity<EntregaDTOInsert> insert(@Valid @RequestBody EntregaDTOInsert dto) {
        // Mapeo inicial con ModelMapper (convierte cantidadKg, puntosGenerados, fecha, etc.)
        Entrega entrega = modelMapper.map(dto, Entrega.class);

        // Si no enviaron fecha en el JSON, asignamos la actual
        if (entrega.getFecha() == null) {
            entrega.setFecha(LocalDateTime.now());
        }

        //Vincular los cascarones con sus IDs
        Material material = new Material();
        material.setIdMaterial(dto.getIdMaterial());
        entrega.setMaterial(material);

        CentroAcopio centroAcopio = new CentroAcopio();
        centroAcopio.setIdCentro(dto.getIdCentroAcopio());
        entrega.setCentroAcopio(centroAcopio);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        entrega.setUsuario(usuario);

        //Guardar en BD
        eS.insert(entrega);

        //Retornar DTO de respuesta con el ID generado
        EntregaDTOInsert responseDTO = modelMapper.map(entrega, EntregaDTOInsert.class);
        responseDTO.setIdMaterial(dto.getIdMaterial());
        responseDTO.setIdCentroAcopio(dto.getIdCentroAcopio());
        responseDTO.setIdUsuario(dto.getIdUsuario());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entrega.getIdEntrega())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

}
