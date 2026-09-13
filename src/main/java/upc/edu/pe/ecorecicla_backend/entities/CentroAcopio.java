package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "centros de acopio")
public class CentroAcopio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCentroAcopio;
    //Falta atributos



    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public CentroAcopio() {}

    public CentroAcopio(Long idCentroAcopio, Usuario usuario) {
        this.idCentroAcopio = idCentroAcopio;
        this.usuario = usuario;
    }

    public Long getIdCentroAcopio() {
        return idCentroAcopio;
    }

    public void setIdCentroAcopio(Long idCentroAcopio) {
        this.idCentroAcopio = idCentroAcopio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
