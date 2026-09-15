package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.entities.Usuarios;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public void insert (Usuarios users);
    public List<Usuarios> list();
    public void delete(Long id);
    public void update(Usuarios users);
    public Optional<Usuarios> listId(Long id);
}
