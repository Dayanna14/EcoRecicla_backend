package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.entities.Entrega;

import java.util.List;

public interface IEntregaService {


    public void insert(Entrega entrega);
    public List<Entrega> list();


}
