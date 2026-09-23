package upc.edu.pe.ecorecicla_backend.dtos;

import java.time.LocalDateTime;

public class EntregaPorFechaDTO {

    private Long idEntrega;
    private LocalDateTime fecha;
    private String nombreCentro;
    private String nombreMaterial;
    private Double cantidadKg;
    private Integer puntosGenerados;

    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public Double getCantidadKg() {
        return cantidadKg;
    }

    public void setCantidadKg(Double cantidadKg) {
        this.cantidadKg = cantidadKg;
    }

    public Integer getPuntosGenerados() {
        return puntosGenerados;
    }

    public void setPuntosGenerados(Integer puntosGenerados) {
        this.puntosGenerados = puntosGenerados;
    }
}