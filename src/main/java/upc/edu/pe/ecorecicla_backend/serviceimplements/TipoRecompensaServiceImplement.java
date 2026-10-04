package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.TipoRecompensa;
import upc.edu.pe.ecorecicla_backend.repositories.TipoRecompensaRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ITipoRecompensaService;

import java.util.List;

@Service
public class TipoRecompensaServiceImplement implements ITipoRecompensaService {

    private final TipoRecompensaRepository tR;

    public TipoRecompensaServiceImplement(TipoRecompensaRepository tR) {
        this.tR = tR;
    }

    @Override
    public void insert(TipoRecompensa t) {
        tR.save(t);
    }

    @Override
    public void update(TipoRecompensa t) {
        tR.save(t);
    }

    @Override
    public void delete(Long id) {
        tR.deleteById(id);
    }

    @Override
    public TipoRecompensa listId(Long id) {
        return tR.findById(id).orElse(null);
    }

    @Override
    public List<TipoRecompensa> list() {
        return tR.findAll();
    }

    @Override
    public List<TipoRecompensa> buscarPorNombre(String nombre) {
        return tR.findByNombreTipoRecompensaContainingIgnoreCase(nombre);
    }

    @Override
    public List<Object[]> buscarRecompensaPorEstado(Boolean estado) {
        return tR.buscarRecompensaPorEstado(estado);
    }

    @Override
    public List<Object[]> tipoRecompensaMasUsado() {
        return tR.tipoRecompensaMasUsado();
    }
}
