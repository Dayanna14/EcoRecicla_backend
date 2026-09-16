package upc.edu.pe.ecorecicla_backend.dtos;

public class MaterialDTOList {
    private Long idMaterial;
    private String nombre;
    private int puntosPorKg;
    private Double factorCo2;
    private String descripcion;
    private Boolean estado;

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

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
