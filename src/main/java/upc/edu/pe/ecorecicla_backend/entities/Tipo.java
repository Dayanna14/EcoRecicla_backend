package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Tipo")
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipo;
    @Column(name = "nombreTipo", length = 50, nullable = false)
    private String nombreTipo;
    @Column(name = "descripcionTipo", length = 200, nullable = false)
    private String descripcionTipo;

    public Tipo() {
    }

    public Tipo(Long idTipo, String nombreTipo, String descripcionTipo) {
        this.idTipo = idTipo;
        this.nombreTipo = nombreTipo;
        this.descripcionTipo = descripcionTipo;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }
}
