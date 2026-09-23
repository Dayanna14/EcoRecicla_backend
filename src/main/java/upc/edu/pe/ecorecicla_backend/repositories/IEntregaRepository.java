package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import upc.edu.pe.ecorecicla_backend.entities.Entrega;

import java.time.LocalDate;
import java.util.List;

public interface IEntregaRepository extends JpaRepository<Entrega, Long> {


    @Query(value = "SELECT e.id_entrega, e.fecha, c.nombre AS centro, m.nombre AS material, e.cantidad_kg, e.puntos_generedos " +
            "FROM entregas e " +
            "INNER JOIN centros_acopio c ON e.id_centro = c.id_centro " +
            "INNER JOIN materiales m ON e.id_material = m.id_material " +
            "WHERE DATE(e.fecha) = :fecha", nativeQuery = true)
    public List<Object[]> buscarPorFecha(@Param("fecha") LocalDate fecha);
}
