package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.repositories.IEntregaRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IEntregaService;

import java.util.List;

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

}
