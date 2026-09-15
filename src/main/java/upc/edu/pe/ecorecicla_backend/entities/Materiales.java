package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Materiales")
public class Materiales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaterial;
    @Column(name = "nombreMaterial", length = 35, nullable = false)
    private String nombreMaterial;
    @Column(name = "puntosKg", nullable = false)
    private int puntosKg;
    @Column(name = "factorCO2", nullable = false)
    private BigDecimal factorCO2;
    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;
    @Column(name = "estadoMaterial", nullable = false)
    private Boolean estadoMaterial;

    public Materiales() {
    }

    public Materiales(Long idMaterial, String nombreMaterial, int puntosKg, BigDecimal factorCO2, String descripcion, Boolean estadoMaterial) {
        this.idMaterial = idMaterial;
        this.nombreMaterial = nombreMaterial;
        this.puntosKg = puntosKg;
        this.factorCO2 = factorCO2;
        this.descripcion = descripcion;
        this.estadoMaterial = estadoMaterial;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public int getPuntosKg() {
        return puntosKg;
    }

    public void setPuntosKg(int puntosKg) {
        this.puntosKg = puntosKg;
    }

    public BigDecimal getFactorCO2() {
        return factorCO2;
    }

    public void setFactorCO2(BigDecimal factorCO2) {
        this.factorCO2 = factorCO2;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstadoMaterial() {
        return estadoMaterial;
    }

    public void setEstadoMaterial(Boolean estadoMaterial) {
        this.estadoMaterial = estadoMaterial;
    }
}



