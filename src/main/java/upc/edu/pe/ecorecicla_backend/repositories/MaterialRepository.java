package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.Material;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>{

    @Query(value = """
        SELECT *
        FROM materiales m
        WHERE m.nombre LIKE CONCAT('%', :nombre, '%')
        """, nativeQuery = true)
    List<Material> buscarPorNombre(@Param("nombre") String nombre);
    @Query(value = """
        SELECT m.nombre,
               SUM(e.cantidad_kg) AS total_kg_reciclados,
               COUNT(e.id_entrega) AS cantidad_entregas
        FROM materiales m
        INNER JOIN entregas e ON m.id_material = e.id_material
        GROUP BY m.nombre
        ORDER BY total_kg_reciclados DESC
        """, nativeQuery = true)
    List<Object[]> materialMasReciclado();

}
