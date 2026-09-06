package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.entities.Rol;

import java.util.List;

public interface IRolService {
    public void insert(Rol r);
    public List<Rol> list();
    Rol searchId(Long id);
    void delete(Long id);
    void update(Rol r);
}
