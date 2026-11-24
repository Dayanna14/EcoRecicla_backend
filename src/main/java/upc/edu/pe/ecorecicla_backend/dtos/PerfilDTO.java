package upc.edu.pe.ecorecicla_backend.dtos;


// Datos del usuario que inició sesión (pantalla "Mi perfil")
public class PerfilDTO {
    private Long idUsuario;

    private String nombre;

    private String email;

    private String rol;

    private Boolean debeCambiarContrasenia;

    public PerfilDTO() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getDebeCambiarContrasenia() {
        return debeCambiarContrasenia;
    }

    public void setDebeCambiarContrasenia(Boolean debeCambiarContrasenia) {
        this.debeCambiarContrasenia = debeCambiarContrasenia;
    }
}
