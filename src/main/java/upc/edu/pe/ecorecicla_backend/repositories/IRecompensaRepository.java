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
           tr.name_tipo_recompensa,
           r.costo_puntos
    FROM recompensas r
    INNER JOIN tipo_recompensas tr
        ON r.id_tipo_recompensa = tr.id_tipo_recompensa
    WHERE r.estado = true
    ORDER BY r.costo_puntos DESC
    LIMIT 5
    """, nativeQuery = true)
    List<Object[]> recompensasMayorCosto();
}
