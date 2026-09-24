package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.Recompensa;

import java.util.List;

@Repository
public interface IRecompensaRepository extends JpaRepository<Recompensa, Long> {
    @Query(value = """
  SELECT r.nombre,
  COUNT(c.id_canje) AS total_canjes
   FROM canjes c
  INNER JOIN recompensas r
   ON c.id_recompensa = r.id_recompensa
    GROUP BY r.nombre
    ORDER BY total_canjes DESC
    """, nativeQuery = true)
    List<Object[]> tipoRecompensaMasCanjeado();
}
