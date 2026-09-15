package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @ManyToOne
    @JoinColumn(name = "idRol")
    private Rol rol;

    @Column(name = "nombre",length = 35,nullable = false)
    private String nombre;
    @Column(name = "email",length = 70,nullable = false)
    private String email;
    @Column(name = "contrasenia",length = 70,nullable = false )
    private String contrasenia;

    public Usuarios() {
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
