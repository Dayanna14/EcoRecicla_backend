package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.entities.Canje;

import java.util.List;
import java.util.Optional;

public interface ICanjeService {
    void insert(Canje canje);
    List<Canje> list();
    void delete(Long id);
    Optional<Canje> listId(Long id);
    void update(Canje canje);
    List<Canje> buscarPorEstado(String estado);
    List<Object[]> canjesPorUsuario();
}