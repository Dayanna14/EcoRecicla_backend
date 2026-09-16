package upc.edu.pe.ecorecicla_backend.dtos;

import jakarta.validation.constraints.NotBlank;

public class TipoRecompensaDTOInsert {
    @NotBlank(message = "El nombre del tipo de recompensa es obligatorio")
    private String nombreTipoRecompensa;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    public String getNombreTipoRecompensa() {
        return nombreTipoRecompensa;
    }

    public void setNombreTipoRecompensa(String nombreTipoRecompensa) {
        this.nombreTipoRecompensa = nombreTipoRecompensa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
