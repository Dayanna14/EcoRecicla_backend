package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "acopios")
public class Canje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCanje;
    //Falta atributos



    @ManyToOne
    @JoinColumn(name = "idRecompensa")
    private Recompensa recompensa;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;


    public Canje() {}

    public Long getIdCanje() {
        return idCanje;
    }

    public void setIdCanje(Long idCanje) {
        this.idCanje = idCanje;
    }

    public Recompensa getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
