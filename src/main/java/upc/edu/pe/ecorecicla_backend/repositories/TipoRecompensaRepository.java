package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.TipoRecompensa;

import java.util.List;

@Repository
public interface TipoRecompensaRepository extends JpaRepository<TipoRecompensa, Long> {
    List<TipoRecompensa> findByNombreTipoRecompensaContainingIgnoreCase(String nombre);

    @Query(value = """

            SELECT r.*
        FROM recompensas r
        INNER JOIN tipo_recompensas t
        ON r.id_tipo_recompensa = t.id_tipo_recompensa
        WHERE r.estado = ?;
        """, nativeQuery = true)
    List<Object[]> buscarRecompensaPorEstado(@Param("estado") Boolean estado);

    @Query(value = """

            SELECT t.name_tipo_recompensa,
               COUNT(r.id_recompensa) AS cantidad_canjes,
               SUM(r.costo_puntos) AS total_puntos_canjeados
        FROM tipo_recompensas t
        INNER JOIN recompensas r
        ON t.id_tipo_recompensa = r.id_tipo_recompensa
        GROUP BY t.name_tipo_recompensa
        ORDER BY cantidad_canjes DESC;
        """, nativeQuery = true)
    List<Object[]> tipoRecompensaMasUsado();
}
