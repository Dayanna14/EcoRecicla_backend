package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    //Falta atributos


    @ManyToOne
    @JoinColumn(name = "idRol")
    private Rol rol;

    public Usuario() {}

    public Usuario(Long idUsuario, Rol rol) {
        this.idUsuario = idUsuario;
        this.rol = rol;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
