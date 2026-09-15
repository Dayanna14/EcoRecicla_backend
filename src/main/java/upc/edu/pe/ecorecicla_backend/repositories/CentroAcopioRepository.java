package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.CentroAcopio;

import java.util.List;

@Repository
public interface CentroAcopioRepository extends JpaRepository<CentroAcopio, Long> {
    List<CentroAcopio> findByEstado(Boolean estado);
    @Query(value = """
        SELECT c.nombre,
               SUM(e.puntos_generedos) AS total_puntos
        FROM centros_acopio c
        INNER JOIN entregas e
        ON c.id_centro = e.id_centro
        GROUP BY c.nombre
        ORDER BY total_puntos DESC
        """, nativeQuery = true)
    List<Object[]> centrosConMasPuntos();
}
