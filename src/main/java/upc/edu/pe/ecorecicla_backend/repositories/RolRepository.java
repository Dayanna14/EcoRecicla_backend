package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.Rol;

import java.util.List;

@Repository

public interface RolRepository extends JpaRepository<Rol,Long> {
    List<Rol> findByNombreContainingIgnoreCase(String nombre);

    //Mostrar el rol más utilizado
    @Query(value = """
        SELECT r.nombre,
               COUNT(u.id_usuario) AS cantidad_usuarios
        FROM rol r
        INNER JOIN usuarios u
        ON r.id_rol = u.id_rol
        GROUP BY r.nombre
        ORDER BY cantidad_usuarios DESC
        """, nativeQuery = true)
    List<Object[]> rolMasUtilizado();

}
