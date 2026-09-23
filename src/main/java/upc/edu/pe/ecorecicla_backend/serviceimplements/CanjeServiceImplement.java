package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.Canje;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.repositories.ICanjeRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ICanjeService;

import java.util.List;
import java.util.Optional;

@Service
public class CanjeServiceImplement implements ICanjeService {
    private final ICanjeRepository cR;

    public CanjeServiceImplement(ICanjeRepository cR) {
        this.cR = cR;
    }

    @Override
    public void insert(Canje canje) {
        cR.save(canje);
    }

    @Override
    public List<Canje> list() {
        return cR.findAll();
    }

    @Override
    public void delete(Long id) {
        cR.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró el canje con el id: " + id)
        );
        cR.deleteById(id);
    }

    @Override
    public Optional<Canje> listId(Long id) {
        return cR.findById(id);
    }

    @Override
    public void update(Canje canje) {
        cR.findById(canje.getIdCanje()).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró el canje con el id: " + canje.getIdCanje())
        );
        cR.save(canje);
    }

    @Override
    public List<Canje> buscarPorEstado(String estado) {
        return cR.findByEstadoIgnoreCase(estado);
    }

    @Override
    public List<Object[]> canjesPorUsuario() {
        return cR.canjesPorUsuario();
    }
}
