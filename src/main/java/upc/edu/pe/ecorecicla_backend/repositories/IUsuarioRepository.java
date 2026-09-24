package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByNombre(String nombre);
    @Query(value = """
    SELECT enabled, COUNT(*)
    FROM usuarios
    GROUP BY enabled
    """, nativeQuery = true)
    List<Object[]> usuariosPorEstado();
}
