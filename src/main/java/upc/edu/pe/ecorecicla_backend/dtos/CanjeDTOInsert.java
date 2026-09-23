package upc.edu.pe.ecorecicla_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class CanjeDTOInsert {
    private Long idCanje;
    private LocalDateTime fecha;
    @NotNull(message = "Los puntos usados son obligatorios")
    @Positive(message = "Los puntos usados deben ser mayores a 0")
    private int puntosUsados;
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
    @NotNull(message = "El id de la recompensa es obligatorio")
    private Long idRecompensa;
    @NotNull(message = "El id del usuario es obligatorio")
    private Long idUsuario;

    public Long getIdCanje() {
        return idCanje;
    }

    public void setIdCanje(Long idCanje) {
        this.idCanje = idCanje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getPuntosUsados() {
        return puntosUsados;
    }

    public void setPuntosUsados(int puntosUsados) {
        this.puntosUsados = puntosUsados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdRecompensa() {
        return idRecompensa;
    }

    public void setIdRecompensa(Long idRecompensa) {
        this.idRecompensa = idRecompensa;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}