package upc.edu.pe.ecorecicla_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuariosDTO {
    private Long id_usuario;
    @NotNull(message = "El id de rol es obligatorio.")
    private Long idRol;
    @NotBlank(message = "Nombre de usuario obligatorio.")
    private String nombre;
    @NotBlank(message = "Email obligatorio.")
    private String email;
    @NotBlank(message = "Contraseña obligatoria.")
    private String contrasenia;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
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
