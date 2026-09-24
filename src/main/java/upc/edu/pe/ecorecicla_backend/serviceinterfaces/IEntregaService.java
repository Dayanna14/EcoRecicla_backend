package upc.edu.pe.ecorecicla_backend.serviceinterfaces;

import upc.edu.pe.ecorecicla_backend.dtos.EntregaDetalladaUsuarioyMaterialDTO;
import upc.edu.pe.ecorecicla_backend.dtos.EntregaPorFechaDTO;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IEntregaService {


    public void insert(Entrega entrega);
    public List<Entrega> list();
    public void delete(Long id);
    public Optional<Entrega> listId(Long id);
    public void update(Entrega entrega);

    public List<Object[]> buscarPorFecha(LocalDate fecha);
    public List<Object[]> obtenerReporteDetallado(Long idUsuario, Long idMaterial);

}
