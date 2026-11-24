package upc.edu.pe.ecorecicla_backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upc.edu.pe.ecorecicla_backend.dtos.CentroPublicoDTO;
import upc.edu.pe.ecorecicla_backend.dtos.ImpactoDTO;
import upc.edu.pe.ecorecicla_backend.dtos.RecompensaListDTO;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ICentroAcopioService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IEntregaService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRecompensaService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IUsuarioService;

import java.util.List;

// Endpoints de solo lectura para la página de inicio: no requieren iniciar sesión
// (ver SecurityConfig) y nunca devuelven datos personales.
@RestController
@RequestMapping("/api/publico")
public class PublicoController {
    private final IEntregaService eS;
    private final ICentroAcopioService caS;
    private final IRecompensaService rS;
    private final IUsuarioService uS;
    private final ModelMapper modelMapper;

    public PublicoController(IEntregaService eS, ICentroAcopioService caS, IRecompensaService rS,
                             IUsuarioService uS, ModelMapper modelMapper) {
        this.eS = eS;
        this.caS = caS;
        this.rS = rS;
        this.uS = uS;
        this.modelMapper = modelMapper;
    }

    // Impacto de la comunidad: kg reciclados, CO2 evitado, entregas, recicladores y centros activos
    @GetMapping("/impacto")
    public ResponseEntity<ImpactoDTO> impacto() {
        List<Entrega> entregas = eS.list();

        double kg = entregas.stream()
                .mapToDouble(e -> e.getCantidadKg() != null ? e.getCantidadKg() : 0)
                .sum();

        double co2 = entregas.stream()
                .filter(e -> e.getMaterial() != null && e.getMaterial().getFactorCo2() != null)
                .mapToDouble(e -> (e.getCantidadKg() != null ? e.getCantidadKg() : 0) * e.getMaterial().getFactorCo2())
                .sum();

        long recicladores = uS.list().stream()
                .filter(u -> Boolean.TRUE.equals(u.getEnabled())
                        && u.getRol() != null
                        && "ROLE_RECICLADOR".equals(u.getRol().getNombre()))
                .count();

        long centros = caS.list().stream()
                .filter(c -> Boolean.TRUE.equals(c.getEstado()))
                .count();

        ImpactoDTO dto = new ImpactoDTO();
        dto.setKgReciclados(Math.round(kg * 10) / 10.0);
        dto.setCo2Evitado(Math.round(co2 * 10) / 10.0);
        dto.setEntregas((long) entregas.size());
        dto.setRecicladores(recicladores);
        dto.setCentrosActivos(centros);

        return ResponseEntity.ok(dto);
    }

    // Centros activos para el mapa de la página de inicio
    @GetMapping("/centros")
    public ResponseEntity<List<CentroPublicoDTO>> centros() {
        List<CentroPublicoDTO> lista = caS.list().stream()
                .filter(c -> Boolean.TRUE.equals(c.getEstado()))
                .map(c -> {
                    CentroPublicoDTO dto = new CentroPublicoDTO();
                    dto.setIdCentro(c.getIdCentro());
                    dto.setNombre(c.getNombre());
                    dto.setLatitud(c.getLatitud());
                    dto.setLongitud(c.getLongitud());
                    dto.setHorario(c.getHorario());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(lista);
    }

    // Recompensas activas para mostrar lo que se puede ganar
    @GetMapping("/recompensas")
    public ResponseEntity<List<RecompensaListDTO>> recompensas() {
        List<RecompensaListDTO> lista = rS.list().stream()
                .filter(r -> r.isEstado())
                .map(r -> modelMapper.map(r, RecompensaListDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }
}
