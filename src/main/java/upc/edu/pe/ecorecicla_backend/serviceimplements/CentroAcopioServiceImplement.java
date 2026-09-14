package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.CentroAcopio;
import upc.edu.pe.ecorecicla_backend.exceptions.ResourceNotFoundException;
import upc.edu.pe.ecorecicla_backend.repositories.CentroAcopioRepository;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.ICentroAcopioService;

import java.util.List;

@Service
public class CentroAcopioServiceImplement implements ICentroAcopioService {
    private final CentroAcopioRepository cR;

    public CentroAcopioServiceImplement(CentroAcopioRepository cR) {
        this.cR = cR;
    }

    @Override
    public void insert(CentroAcopio c) {
        cR.save(c);
    }

    @Override
    public List<CentroAcopio> list() {
        return cR.findAll();
    }

    @Override
    public CentroAcopio searchId(Long id) {
        return cR.findById(id).orElseThrow(() ->
        new ResourceNotFoundException(
                "Centro de acopio no encontrado"));
    }

    @Override
    public void delete(Long id) {
        CentroAcopio centro = searchId(id);
        cR.delete(centro);
    }

    @Override
    public void update(CentroAcopio c) {
        cR.findById(c.getIdCentro())
                .orElseThrow(() ->
        new ResourceNotFoundException(
                "Centro de acopio no encontrado"));
        cR.save(c);
    }

    @Override
    public List<CentroAcopio> buscarPorEstado(Boolean estado) {
        return cR.findByEstado(estado);
    }

    @Override
    public List<Object[]> centrosConMasPuntos() {
        return cR.centrosConMasPuntos();
    }
}
