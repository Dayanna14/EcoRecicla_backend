package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "recompensas")
public class Recompensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecompensa;
    //Falta atributos


    @ManyToOne
    @JoinColumn(name = "idTipoRecompensa")
    private TipoRecompensa tipoRecompensa;

    public Recompensa() {}

    public Recompensa(Long idRecompensa, TipoRecompensa tipoRecompensa) {
        this.idRecompensa = idRecompensa;
        this.tipoRecompensa = tipoRecompensa;
    }

    public Long getIdRecompensa() {
        return idRecompensa;
    }

    public void setIdRecompensa(Long idRecompensa) {
        this.idRecompensa = idRecompensa;
    }

    public TipoRecompensa getTipoRecompensa() {
        return tipoRecompensa;
    }

    public void setTipoRecompensa(TipoRecompensa tipoRecompensa) {
        this.tipoRecompensa = tipoRecompensa;
    }
}
