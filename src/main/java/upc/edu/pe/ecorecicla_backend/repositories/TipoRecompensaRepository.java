package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.TipoRecompensa;

@Repository
public interface TipoRecompensaRepository extends JpaRepository<TipoRecompensa, Long> {
}
