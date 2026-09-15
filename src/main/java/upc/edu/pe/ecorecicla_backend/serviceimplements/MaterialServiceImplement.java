package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.Material;
import upc.edu.pe.ecorecicla_backend.repositories.MaterialRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IMaterialService;

import java.util.List;

@Service
public class MaterialServiceImplement implements IMaterialService {

    private final MaterialRepository mR;

    public MaterialServiceImplement(MaterialRepository mR) {
        this.mR = mR;
    }

    @Override
    public void insert(Material m) {
        mR.save(m);
    }

    @Override
    public void update(Material m) {
        mR.save(m);
    }

    @Override
    public void delete(Long id) {
        mR.deleteById(id);
    }

    @Override
    public Material listId(Long id) {
        return mR.findById(id).orElse(null);
    }

    @Override
    public List<Material> list() {
        return mR.findAll();
    }
}
