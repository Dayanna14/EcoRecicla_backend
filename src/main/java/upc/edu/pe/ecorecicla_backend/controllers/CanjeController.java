package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import upc.edu.pe.ecorecicla_backend.dtos.CanjeDTOInsert;
import upc.edu.pe.ecorecicla_backend.dtos.CanjeDTOList;
import upc.edu.pe.ecorecicla_backend.dtos.CanjePorUsuarioDTO;
import upc.edu.pe.ecorecicla_backend.entities.Canje;
import upc.edu.pe.ecorecicla_backend.entities.Recompensa;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ICanjeService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IEntregaService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRecompensaService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/canjes")
public class CanjeController {
    private final ICanjeService cS;
    private final ModelMapper modelMapper;
    private final IRecompensaService rS;
    private final IEntregaService eS;

    public CanjeController(ICanjeService cS, ModelMapper modelMapper, IRecompensaService rS, IEntregaService eS) {
        this.cS = cS;
        this.modelMapper = modelMapper;
        this.rS = rS;
        this.eS = eS;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
    @GetMapping
    public ResponseEntity<List<CanjeDTOList>> list() {
        List<CanjeDTOList> listDTO = cS.list().stream()
                .map(canje -> modelMapper.map(canje, CanjeDTOList.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDTO);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','RECICLADOR')")
    @PostMapping
    public ResponseEntity<CanjeDTOInsert> insert(@Valid @RequestBody CanjeDTOInsert dto) {
        Canje canje = modelMapper.map(dto, Canje.class);

        if (canje.getFecha() == null) {
            canje.setFecha(LocalDateTime.now());
        }

        Recompensa recompensa = rS.listId(dto.getIdRecompensa())
                .orElseThrow(() ->
                        new ResourceNotFoundException("La recompensa no existe."));

        if (!recompensa.isEstado()) {
            throw new IllegalArgumentException("La recompensa no está disponible.");
        }

        // Puntos ganados por el usuario en sus entregas
        int puntosGanados = eS.list().stream()
                .filter(e -> e.getUsuario() != null
                        && e.getUsuario().getId_usuario().equals(dto.getIdUsuario()))
                .mapToInt(e -> e.getPuntosGeneredos())
                .sum();

        // Puntos que ya gastó en canjes anteriores
        int puntosGastados = cS.list().stream()
                .filter(c -> c.getUsuario() != null
                        && c.getUsuario().getId_usuario().equals(dto.getIdUsuario()))
                .mapToInt(c -> c.getPuntosUsados())
                .sum();

        int puntosDisponibles = puntosGanados - puntosGastados;

        if (puntosDisponibles < recompensa.getCostoPuntos()) {
            throw new IllegalArgumentException(
                    "Puntos insuficientes. Disponibles: " + puntosDisponibles
                            + ", necesarios: " + recompensa.getCostoPuntos());
        }

        canje.setRecompensa(recompensa);
        canje.setPuntosUsados(recompensa.getCostoPuntos());

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

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CanjeDTOList> listId(@PathVariable("id") Long id) {
        Canje canje = cS.listId(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró el canje con el id: " + id)
        );
        CanjeDTOList dto = modelMapper.map(canje, CanjeDTOList.class);
        return ResponseEntity.ok(dto);
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        cS.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping
    public ResponseEntity<CanjeDTOInsert> update(@Valid @RequestBody CanjeDTOInsert dto) {
        if (dto.getIdCanje() == null) {
            cS.listId(dto.getIdCanje()).orElseThrow(() ->
                    new ResourceNotFoundException("No se encontró el canje con el id: " + dto.getIdCanje()));
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
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
    @GetMapping("/buscar")
    public ResponseEntity<List<CanjeDTOList>> buscarPorEstado(@RequestParam String estado) {
        List<CanjeDTOList> lista = cS.buscarPorEstado(estado).stream()
                .map(canje -> modelMapper.map(canje, CanjeDTOList.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
    @GetMapping("/canjes-por-usuario")
    public ResponseEntity<List<CanjePorUsuarioDTO>> canjesPorUsuario() {
        List<CanjePorUsuarioDTO> lista = cS.canjesPorUsuario()
                .stream()
                .map(item -> {
                    CanjePorUsuarioDTO dto = new CanjePorUsuarioDTO();
                    dto.setNombreUsuario((String) item[0]);
                    dto.setCantidadCanjes(((Number) item[1]).intValue());
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(lista);
    }
}
