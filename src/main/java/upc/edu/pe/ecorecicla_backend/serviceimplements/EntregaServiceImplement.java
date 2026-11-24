package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaDetalladaUsuarioyMaterialDTO;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaPorFechaDTO;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;
import upc.edu.pe.ecorecicla_backend.entities.Material;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.repositories.IEntregaRepository;
import upc.edu.pe.ecorecicla_backend.repositories.MaterialRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IEntregaService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaServiceImplement implements IEntregaService {

    private final IEntregaRepository eR;
    private final MaterialRepository mR;

    public EntregaServiceImplement(IEntregaRepository eR, MaterialRepository mR) {
        this.eR = eR;
        this.mR = mR;
    }

    @Override
    public void insert(Entrega entrega) {
        // Validar que el objeto material y su ID existan de forma segura
        if (entrega.getMaterial() == null || entrega.getMaterial().getIdMaterial() == null) {
            throw new IllegalArgumentException("La entrega debe tener un material válido.");
        }

        Long idMat = entrega.getMaterial().getIdMaterial();

        Material material = mR.findById(idMat)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el material con ID: " + idMat));

        // Calcular automáticamente los puntos: cantidadKg * puntosPorKg
        int puntosCalculados = (int) (entrega.getCantidadKg() * material.getPuntosPorKg());
        entrega.setPuntosGeneredos(puntosCalculados);

        // Asignar la fecha actual si no viene establecida
        if (entrega.getFecha() == null) {
            entrega.setFecha(LocalDateTime.now());
        }

        eR.save(entrega);
    }

    @Override
    public List<Entrega> list() {
        return eR.findAll();
    }

    @Override
    public void delete(Long id) {
        eR.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró la entrega con el id: " + id)
        );
        eR.deleteById(id);
    }

    @Override
    public Optional<Entrega> listId(Long id) {
        return eR.findById(id);
    }

    @Override
    public void update(Entrega entrega) {
        eR.findById(entrega.getIdEntrega()).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró la entrega con el id: " + entrega.getIdEntrega())
        );

        if (entrega.getMaterial() == null || entrega.getMaterial().getIdMaterial() == null) {
            throw new IllegalArgumentException("La entrega debe tener un material válido.");
        }

        Long idMat = entrega.getMaterial().getIdMaterial();

        Material material = mR.findById(idMat)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el material con ID: " + idMat));

        // Recalcular los puntos al actualizar
        int puntosCalculados = (int) (entrega.getCantidadKg() * material.getPuntosPorKg());
        entrega.setPuntosGeneredos(puntosCalculados);

        if (entrega.getFecha() == null) {
            entrega.setFecha(LocalDateTime.now());
        }

        eR.save(entrega);
    }

    @Override
    public List<Object[]> buscarPorFecha(LocalDate fecha) {
        return eR.buscarPorFecha(fecha);
    }

    @Override
    public List<Object[]> obtenerReporteDetallado(Long idUsuario, Long idMaterial) {
        return eR.obtenerReporteDetallado(idUsuario, idMaterial);
    }

    @Override
    public Long contarEntregas100PuntosGenerados() {
        return eR.contarEntregas100PuntosGenerados();
    }

    @Override
    public List<Entrega> listarPorUsuario(Long idUsuario) {
        return eR.listarPorUsuario(idUsuario);
    }

}
