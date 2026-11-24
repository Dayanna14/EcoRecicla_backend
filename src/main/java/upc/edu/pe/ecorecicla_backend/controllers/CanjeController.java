package upc.edu.pe.ecorecicla_backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IUsuarioService;

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
    private final IUsuarioService uS;

    public CanjeController(ICanjeService cS, ModelMapper modelMapper, IRecompensaService rS, IEntregaService eS, IUsuarioService uS) {
        this.cS = cS;
        this.modelMapper = modelMapper;
        this.rS = rS;
        this.eS = eS;
        this.uS = uS;
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
    public ResponseEntity<CanjeDTOInsert> insert(@Valid @RequestBody CanjeDTOInsert dto, Authentication authentication) {
        // El reciclador solo puede canjear para sí mismo y su canje siempre empieza PENDIENTE
        if (esSoloReciclador(authentication)) {
            dto.setIdUsuario(usuarioActual(authentication).getId_usuario());
            dto.setEstado("PENDIENTE");
        }

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
        // (los canjes CANCELADOS devuelven sus puntos, por eso no se cuentan)
        int puntosGastados = cS.list().stream()
                .filter(c -> c.getUsuario() != null
                        && c.getUsuario().getId_usuario().equals(dto.getIdUsuario())
                        && !"CANCELADO".equalsIgnoreCase(c.getEstado()))
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
            throw new IllegalArgumentException("El idCanje no puede ser nulo para actualizar.");
        }

        Canje existente = cS.listId(dto.getIdCanje()).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró el canje con el id: " + dto.getIdCanje()));

        Canje canje = modelMapper.map(dto, Canje.class);

        // Si no enviaron fecha, se conserva la que ya tenía el canje
        if (canje.getFecha() == null) {
            canje.setFecha(existente.getFecha());
        }

        Recompensa recompensa = rS.listId(dto.getIdRecompensa())
                .orElseThrow(() ->
                        new ResourceNotFoundException("La recompensa no existe."));
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

    // Canjes del usuario que inició sesión
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO','RECICLADOR')")
    @GetMapping("/mis-canjes")
    public ResponseEntity<List<CanjeDTOList>> misCanjes(Authentication authentication) {
        List<CanjeDTOList> lista = cS.listarPorUsuario(usuarioActual(authentication).getId_usuario())
                .stream()
                .map(canje -> modelMapper.map(canje, CanjeDTOList.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // El reciclador cancela SU canje pendiente (recupera los puntos); el admin puede cancelar cualquiera
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','RECICLADOR')")
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<CanjeDTOList> cancelar(@PathVariable("id") Long id, Authentication authentication) {
        Canje canje = cS.listId(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró el canje con el id: " + id));

        if (esSoloReciclador(authentication)
                && !canje.getUsuario().getId_usuario().equals(usuarioActual(authentication).getId_usuario())) {
            throw new AccessDeniedException("No puedes cancelar un canje que no es tuyo.");
        }
        if (!"PENDIENTE".equalsIgnoreCase(canje.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden cancelar canjes pendientes.");
        }

        canje.setEstado("CANCELADO");
        cS.update(canje);
        return ResponseEntity.ok(modelMapper.map(canje, CanjeDTOList.class));
    }

    // El operador (o el admin) entrega la recompensa en el centro y marca el canje como completado
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERADOR_CENTRO')")
    @PutMapping("/{id}/completar")
    public ResponseEntity<CanjeDTOList> completar(@PathVariable("id") Long id) {
        Canje canje = cS.listId(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró el canje con el id: " + id));

        if (!"PENDIENTE".equalsIgnoreCase(canje.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden completar canjes pendientes.");
        }

        canje.setEstado("COMPLETADO");
        cS.update(canje);
        return ResponseEntity.ok(modelMapper.map(canje, CanjeDTOList.class));
    }

    private Usuarios usuarioActual(Authentication authentication) {
        return uS.buscarPorEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario de la sesión."));
    }

    private boolean esSoloReciclador(Authentication authentication) {
        boolean reciclador = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECICLADOR"));
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
        return reciclador && !admin;
    }
}
