package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tipoRecompensa")
public class TipoRecompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoRecompensa;
    @Column(name = "nameTipoRecompensa", length = 50, nullable = false)
    private String nombreTipoRecompensa;
    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;

    public TipoRecompensa() {}

    public TipoRecompensa(Long idTipoRecompensa, String nombreTipoRecompensa, String descripcion) {
        this.idTipoRecompensa = idTipoRecompensa;
        this.nombreTipoRecompensa = nombreTipoRecompensa;
        this.descripcion = descripcion;
    }

    public Long getIdTipoRecompensa() {
        return idTipoRecompensa;
    }

    public void setIdTipoRecompensa(Long idTipoRecompensa) {
        this.idTipoRecompensa = idTipoRecompensa;
    }

    public String getNombreTipoRecompensa() {
        return nombreTipoRecompensa;
    }

    public void setNombreTipoRecompensa(String nombreTipoRecompensa) {
        this.nombreTipoRecompensa = nombreTipoRecompensa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
