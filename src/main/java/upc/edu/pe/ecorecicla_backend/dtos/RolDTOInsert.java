package upc.edu.pe.ecorecicla_backend.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class RolDTOInsert {
    private Long idRol;
    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;
    @NotBlank(message = "El descripcion del rol es obligatorio")
    private String descripcion;

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}