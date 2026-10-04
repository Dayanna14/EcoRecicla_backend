package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.Recompensa;
import upc.edu.pe.ecorecicla_backend.repositories.IRecompensaRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRecompensaService;

import java.util.List;
import java.util.Optional;

@Service
public class RecompensaServiceImplement implements IRecompensaService {

    private final IRecompensaRepository rR;

    public RecompensaServiceImplement(IRecompensaRepository rR) {
        this.rR = rR;
    }

    @Override
    public void insert(Recompensa recom) {
        rR.save(recom);
    }

    @Override
    public List<Recompensa> list() {
        return rR.findAll();
    }

    @Override
    public void delete(Long id) {
        rR.deleteById(id);
    }

    @Override
    public void update(Recompensa recom) {
        rR.save(recom);
    }

    @Override
    public Optional<Recompensa> listId(Long id) {
        return rR.findById(id);
    }

    @Override
    public List<Object[]> tipoRecompensaMasCanjeado() {
        return rR.tipoRecompensaMasCanjeado();
    }
}
