package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "canjes")
public class Canje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCanje;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "puntos_usados", nullable = false)
    private int puntosUsados;

    @Column(name = "estado", length = 50, nullable = false)
    private String estado; // "PENDIENTE", "COMPLETADO", etc.

    @ManyToOne
    @JoinColumn(name = "idRecompensa")
    private Recompensa recompensa;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuarios usuario;

    public Canje() {}

    public Canje(Long idCanje, LocalDateTime fecha, int puntosUsados, String estado, Recompensa recompensa, Usuarios usuario) {
        this.idCanje = idCanje;
        this.fecha = fecha;
        this.puntosUsados = puntosUsados;
        this.estado = estado;
        this.recompensa = recompensa;
        this.usuario = usuario;
    }

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

    public Recompensa getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
