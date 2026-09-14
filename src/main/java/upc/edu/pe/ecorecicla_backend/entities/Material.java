package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "materiales")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaterial;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "puntos_por_kg", nullable = false)
    private int puntosPorKg;

    @Column(name = "factor_co2", nullable = false)
    private Double factorCo2;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado;

    public Material() {
    }

    public Material(Long idMaterial, String nombre, int puntosPorKg,
                    Double factorCo2, String descripcion, Boolean estado) {
        this.idMaterial = idMaterial;
        this.nombre = nombre;
        this.puntosPorKg = puntosPorKg;
        this.factorCo2 = factorCo2;
        this.descripcion = descripcion;
        this.estado = estado;
    }

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