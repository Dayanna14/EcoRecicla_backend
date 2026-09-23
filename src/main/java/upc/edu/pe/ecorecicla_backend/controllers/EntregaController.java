package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaDTOInsert;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaDTOList;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaDetalladaUsuarioyMaterialDTO;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaPorFechaDTO;
import upc.edu.pe.ecorecicla_backend.entities.CentroAcopio;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;
import upc.edu.pe.ecorecicla_backend.entities.Material;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IEntregaService;

import java.net.URI;
import java.time.LocalDate;
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

        return ResponseEntity.ok(listDTO);// HTTP 200 OK
    }

    @PostMapping
    public ResponseEntity<EntregaDTOInsert> insert(@Valid @RequestBody EntregaDTOInsert dto) {

        Entrega entrega = modelMapper.map(dto, Entrega.class);

        // Si no enviaron fecha en el JSON, asignamos la actual
        if (entrega.getFecha() == null) {
            entrega.setFecha(LocalDateTime.now());
        }


        Material material = new Material();
        material.setIdMaterial(dto.getIdMaterial());
        entrega.setMaterial(material);

        CentroAcopio centroAcopio = new CentroAcopio();
        centroAcopio.setIdCentro(dto.getIdCentroAcopio());
        entrega.setCentroAcopio(centroAcopio);

        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(dto.getIdUsuario());
        entrega.setUsuario(usuario);
        

        eS.insert(entrega);


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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        eS.delete(id);
        return ResponseEntity.noContent().build(); //HTTP 204
    }

    @PutMapping
    public ResponseEntity<EntregaDTOInsert> update(@Valid @RequestBody EntregaDTOInsert dto) {

        if (dto.getIdEntrega() == null) {
            throw new IllegalArgumentException("El idEntrega no puede ser nulo para actualizar.");
        }


        Entrega entrega = modelMapper.map(dto, Entrega.class);

        Material material = new Material();
        material.setIdMaterial(dto.getIdMaterial());
        entrega.setMaterial(material);

        CentroAcopio centroAcopio = new CentroAcopio();
        centroAcopio.setIdCentro(dto.getIdCentroAcopio());
        entrega.setCentroAcopio(centroAcopio);

        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(dto.getIdUsuario());
        entrega.setUsuario(usuario);


        eS.update(entrega);


        EntregaDTOInsert responseDTO = modelMapper.map(entrega, EntregaDTOInsert.class);
        responseDTO.setIdMaterial(dto.getIdMaterial());
        responseDTO.setIdCentroAcopio(dto.getIdCentroAcopio());
        responseDTO.setIdUsuario(dto.getIdUsuario());

        return ResponseEntity.ok(responseDTO);// HTTP 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregaDTOList> listId(@PathVariable("id") Long id) {

        Entrega entrega = eS.listId(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró la entrega con el id: " + id)
        );


        EntregaDTOList dto = modelMapper.map(entrega, EntregaDTOList.class);

        // HTTP 200 OK
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar-por-fecha")
    public ResponseEntity<List<EntregaPorFechaDTO>> buscarPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<EntregaPorFechaDTO> lista = eS.buscarPorFecha(fecha)
                .stream()
                .map(item -> {
                    EntregaPorFechaDTO dto = new EntregaPorFechaDTO();
                    dto.setIdEntrega(((Number) item[0]).longValue());
                    dto.setFecha(((java.sql.Timestamp) item[1]).toLocalDateTime());
                    dto.setNombreCentro((String) item[2]);
                    dto.setNombreMaterial((String) item[3]);
                    dto.setCantidadKg(((Number) item[4]).doubleValue());
                    dto.setPuntosGenerados(((Number) item[5]).intValue());
                    return dto;
                }).toList();

        return ResponseEntity.ok(lista);
    }


    @GetMapping("/reporte-detallado Usuario Material")
    public ResponseEntity<List<EntregaDetalladaUsuarioyMaterialDTO>> obtenerReporteDetallado(
            @RequestParam(value = "idUsuario", required = false) Long idUsuario,
            @RequestParam(value = "idMaterial", required = false) Long idMaterial) {

        List<EntregaDetalladaUsuarioyMaterialDTO> lista = eS.obtenerReporteDetallado(idUsuario, idMaterial)
                .stream()
                .map(item -> {
                    EntregaDetalladaUsuarioyMaterialDTO dto = new EntregaDetalladaUsuarioyMaterialDTO();
                    dto.setIdEntrega(((Number) item[0]).longValue());
                    dto.setNombreUsuario((String) item[1]);
                    dto.setNombreMaterial((String) item[2]);
                    dto.setCantidadKg(((Number) item[3]).doubleValue());
                    dto.setPuntosGenerados(((Number) item[4]).intValue());
                    dto.setFecha(((java.sql.Timestamp) item[5]).toLocalDateTime());
                    return dto;
                }).toList();

        return ResponseEntity.ok(lista);
    }

}
