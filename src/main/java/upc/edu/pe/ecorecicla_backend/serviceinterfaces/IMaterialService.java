package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.entities.Material;

import java.util.List;

public interface IMaterialService {
    public void insert(Material m);
    public void update(Material m);
    public void delete(Long id);
    public Material listId(Long id);
    public List<Material> list();

}
