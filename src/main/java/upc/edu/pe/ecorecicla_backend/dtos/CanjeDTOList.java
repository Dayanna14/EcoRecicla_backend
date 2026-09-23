package upc.edu.pe.ecorecicla_backend.dtos;

import java.time.LocalDateTime;

public class CanjeDTOList {
    private Long idCanje;
    private LocalDateTime fecha;
    private int puntosUsados;
    private String estado;
    private Long recompensaIdRecompensa;
    private Long usuarioId_usuario;

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

    public Long getRecompensaIdRecompensa() {
        return recompensaIdRecompensa;
    }

    public void setRecompensaIdRecompensa(Long recompensaIdRecompensa) {
        this.recompensaIdRecompensa = recompensaIdRecompensa;
    }

    public Long getUsuarioId_usuario() {
        return usuarioId_usuario;
    }

    public void setUsuarioId_usuario(Long usuarioId_usuario) {
        this.usuarioId_usuario = usuarioId_usuario;
    }
}
