package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.Canje;

import java.util.List;

@Repository
public interface ICanjeRepository extends JpaRepository<Canje, Long> {
    List<Canje> findByEstadoIgnoreCase(String estado);
}
