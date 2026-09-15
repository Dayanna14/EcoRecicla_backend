package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "centros_acopio")
public class CentroAcopio {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idCentro;

        @Column(name = "nombre", length = 100, nullable = false)
        private String nombre;

        @Column(name = "latitud", nullable = false)
        private Double latitud;

        @Column(name = "longitud", nullable = false)
        private Double longitud;

        @Column(name = "horario", length = 100, nullable = false)
        private String horario;

        @Column(name = "estado", nullable = false)
        private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public CentroAcopio() {
    }

    public CentroAcopio(Long idCentro, String nombre, Double latitud, Double longitud, String horario, Boolean estado, Usuario usuario) {
        this.idCentro = idCentro;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.horario = horario;
        this.estado = estado;
        this.usuario = usuario;
    }

    public Long getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Long idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
