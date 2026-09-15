package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.entities.TipoRecompensa;

import java.util.List;

public interface ITipoRecompensaService {
    void insert(TipoRecompensa t);
    void update(TipoRecompensa t);
    void delete(Long id);
    TipoRecompensa listId(Long id);
    List<TipoRecompensa> list();
}
