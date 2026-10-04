package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.entities.Recompensa;

import java.util.List;
import java.util.Optional;

public interface IRecompensaService {
    public void insert (Recompensa recom);
    public List<Recompensa> list();
    public void delete(Long id);
    public void update(Recompensa recom);
    public Optional<Recompensa> listId(Long id);
    List<Object[]> tipoRecompensaMasCanjeado();
}
