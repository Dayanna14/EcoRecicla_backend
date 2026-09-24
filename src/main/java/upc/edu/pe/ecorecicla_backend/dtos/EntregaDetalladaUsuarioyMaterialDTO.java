


package upc.edu.pe.ecorecicla_backend.dtos;

import java.time.LocalDateTime;

public class EntregaDetalladaUsuarioyMaterialDTO {
    private Long idEntrega;
    private String nombreUsuario;
    private String nombreMaterial;
    private Double cantidadKg;
    private Integer puntosGenerados;
    private LocalDateTime fecha;

    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}