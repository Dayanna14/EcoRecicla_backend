package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.Canje;

import java.util.List;

@Repository
public interface ICanjeRepository extends JpaRepository<Canje, Long> {
    List<Canje> findByEstadoIgnoreCase(String estado);

    @Query(value = """
        SELECT u.nombre,
               COUNT(c.id_canje) AS cantidad_canjes
        FROM usuarios u
        INNER JOIN canjes c
        ON u.id_usuario = c.id_usuario
        GROUP BY u.nombre
        ORDER BY cantidad_canjes DESC
        """, nativeQuery = true)
    List<Object[]> canjesPorUsuario();
}
