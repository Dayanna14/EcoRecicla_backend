package upc.edu.pe.ecorecicla_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MaterialDTOInsert {
    @NotBlank(message = "El nombre del material es obligatorio")
    private String nombre;

    @NotNull(message = "Los puntos por kg son obligatorios")
    private int puntosPorKg;

    @NotNull(message = "El factor de CO2 es obligatorio")
    private Double factorCo2;

    private String descripcion;

    private Boolean estado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntosPorKg() {
        return puntosPorKg;
    }

    public void setPuntosPorKg(int puntosPorKg) {
        this.puntosPorKg = puntosPorKg;
    }

    public Double getFactorCo2() {
        return factorCo2;
    }

    public void setFactorCo2(Double factorCo2) {
        this.factorCo2 = factorCo2;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
