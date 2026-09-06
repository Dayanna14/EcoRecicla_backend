package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.Rol;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.repositories.RolRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IRolService;

import java.util.List;

@Service
public class RolServiceImplement implements IRolService {

    private final RolRepository rR;
    public RolServiceImplement(RolRepository rR) {
        this.rR = rR;
    }

    @Override
    public void insert(Rol r) {
        rR.save(r);
    }

    @Override
    public List<Rol> list() {
        return  rR.findAll();
    }

    @Override
    public Rol searchId(Long id) {
        return rR.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Rol no encontrado"));
    }

    @Override
    public void delete(Long id) {
        Rol rol = rR.findById(id).orElseThrow(()->
                new  ResourceNotFoundException(
                        "Rol no encontrado "));
        rR.delete(rol);
    }

    @Override
    public void update(Rol r) {
        rR.findById(r.getIdRol())
                .orElseThrow(() ->
        new ResourceNotFoundException(
                "Rol no encontrado"));
        rR.save(r);
    }
}
