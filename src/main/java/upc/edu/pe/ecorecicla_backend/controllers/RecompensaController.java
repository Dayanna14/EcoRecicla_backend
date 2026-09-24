package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.RecompensaInsertDTO;
import upc.edu.pe.ecorecicla_backend.dtos.RecompensaListDTO;
import upc.edu.pe.ecorecicla_backend.entities.*;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ITipoRecompensaService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRecompensaService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recompensa")
public class RecompensaController {
    private final IRecompensaService rS;
    private final ModelMapper modelMapper;
    private final ITipoRecompensaService tS;

    public RecompensaController(IRecompensaService rS, ModelMapper modelMapper, ITipoRecompensaService tS) {
        this.rS = rS;
        this.modelMapper = modelMapper;
        this.tS = tS;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<RecompensaListDTO> registrar(
            @Valid @RequestBody RecompensaInsertDTO dto) {

        TipoRecompensa tipo = tS.listId(dto.getIdTipoRecompensa());

        Recompensa recom = modelMapper.map(dto, Recompensa.class);

        recom.setTipoRecompensa(tipo);

        rS.insert(recom);

        RecompensaListDTO responseDTO =
                modelMapper.map(recom, RecompensaListDTO.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recom.getIdRecompensa())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<RecompensaListDTO>> listar() {

        List<RecompensaListDTO> lista = rS.list()
                .stream()
                .map(r -> modelMapper.map(r, RecompensaListDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecompensaListDTO> listId(
            @PathVariable Long id) {

        Recompensa recom = rS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "La recompensa no existe."
                        ));

        RecompensaListDTO responseDTO =
                modelMapper.map(recom, RecompensaListDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        Recompensa recom = rS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "La recompensa no existe."
                        ));

        rS.delete(recom.getIdRecompensa());

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @PutMapping
    public ResponseEntity<RecompensaListDTO> actualizar(
            @Valid @RequestBody RecompensaInsertDTO dto) {

        Optional<Recompensa> existente =
                rS.listId(dto.getIdRecompensa());

        if (existente.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No existe una recompensa con el id: "
                            + dto.getIdRecompensa()
            );
        }

        TipoRecompensa tipo = tS.listId(dto.getIdTipoRecompensa());

        Recompensa recom = existente.get();

        recom.setNombre(dto.getNombre());
        recom.setCostoPuntos(dto.getCostoPuntos());
        recom.setEstado(dto.isEstado());
        recom.setImagenUrl(dto.getImagenUrl());

        rS.update(recom);

        RecompensaListDTO responseDTO =
                modelMapper.map(recom, RecompensaListDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/recompensas-mayor-costo")
    public List<Object[]> recompensasMayorCosto() {
        return rS.recompensasMayorCosto();
    }

}
