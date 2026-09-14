package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.entities.CentroAcopio;

import java.util.List;

public interface ICentroAcopioService {
    void insert(CentroAcopio c);
    List<CentroAcopio> list();
    CentroAcopio searchId(Long id);
    void delete(Long id);
    void update(CentroAcopio c);
    List<CentroAcopio> buscarPorEstado(Boolean estado);
}
