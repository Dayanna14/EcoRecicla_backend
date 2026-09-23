package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaDetalladaUsuarioyMaterialDTO;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaPorFechaDTO;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.repositories.IEntregaRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IEntregaService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaServiceImplement implements IEntregaService {

    private final IEntregaRepository eR;

    public EntregaServiceImplement(IEntregaRepository eR) {
        this.eR = eR;
    }

    @Override
    public void insert(Entrega entrega) {
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
        // Validamos que la entrega a actualizar existe
        eR.findById(entrega.getIdEntrega()).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró la entrega con el id: " + entrega.getIdEntrega())
        );
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



}
