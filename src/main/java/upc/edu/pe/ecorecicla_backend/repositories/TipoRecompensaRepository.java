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
        FROM recompensa r
        INNER JOIN tipoRecompensas t
        ON r.id_tipo = t.idTipoRecompensa
        WHERE r.estado = :estado
        """, nativeQuery = true)
    List<Object[]> buscarRecompensaPorEstado(@Param("estado") Boolean estado);
}
