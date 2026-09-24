package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.repositories.IUsuarioRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IUsuarioService;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImplement implements IUsuarioService {

    private final IUsuarioRepository uR;

    public UsuariosServiceImplement(IUsuarioRepository uR) {
        this.uR = uR;
    }

    @Override
    public void insert(Usuarios users) {
        uR.save(users);
    }

    @Override
    public List<Usuarios> list() {
        return uR.findAll();
    }

    @Override
    public void delete(Long id) {
        uR.deleteById(id);
    }

    @Override
    public void update(Usuarios users) {
        uR.save(users);
    }

    @Override
    public Optional<Usuarios> listId(Long id) {
        return uR.findById(id);
    }

    @Override
    public List<Object[]> usuariosPorRol() {
        return uR.usuariosPorRol();
    }

    @Override
    public List<Object[]> usuariosPorEstado() {
        return uR.usuariosPorEstado();
    }
}
