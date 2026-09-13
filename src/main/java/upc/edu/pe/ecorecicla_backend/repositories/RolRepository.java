package upc.edu.pe.ecorecicla_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecorecicla_backend.entities.Rol;

import java.util.List;

@Repository

public interface RolRepository extends JpaRepository<Rol,Long> {
    List<Rol> findByNombreContainingIgnoreCase(String nombre);
}
