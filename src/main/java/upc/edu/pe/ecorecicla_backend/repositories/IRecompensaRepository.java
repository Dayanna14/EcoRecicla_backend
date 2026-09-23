package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.Recompensa;

@Repository
public interface IRecompensaRepository extends JpaRepository<Recompensa, Long> {
}
