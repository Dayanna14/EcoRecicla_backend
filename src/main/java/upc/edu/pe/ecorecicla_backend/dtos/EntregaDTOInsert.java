package upc.edu.pe.ecorecicla_backend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class EntregaDTOInsert {

    private Long idEntrega;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Double cantidadKg;

    private int puntosGenerados;
    private LocalDateTime fecha;

    @NotNull(message = "El idMaterial es obligatorio")
    private Long idMaterial;

    @NotNull(message = "El idCentroAcopio es obligatorio")
    private Long idCentroAcopio;

    @NotNull(message = "El idUsuario es obligatorio")
    private Long idUsuario;

    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Double getCantidadKg() {
        return cantidadKg;
    }

    public void setCantidadKg(Double cantidadKg) {
        this.cantidadKg = cantidadKg;
    }

    public int getPuntosGenerados() {
        return puntosGenerados;
    }

    public void setPuntosGenerados(int puntosGenerados) {
        this.puntosGenerados = puntosGenerados;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Long getIdCentroAcopio() {
        return idCentroAcopio;
    }

    public void setIdCentroAcopio(Long idCentroAcopio) {
        this.idCentroAcopio = idCentroAcopio;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
