package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;

public interface IEntregaRepository extends JpaRepository<Entrega, Long> {
}
