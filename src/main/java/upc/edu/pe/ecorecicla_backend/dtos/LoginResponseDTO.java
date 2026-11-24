package upc.edu.pe.ecorecicla_backend.dtos;

import java.util.List;

public class LoginResponseDTO {
    private String token;

    private String username;
    private List<String> roles;

    // Datos del usuario para que el front sepa quién es sin pedir otra consulta
    private Long idUsuario;
    private String nombre;
    private Boolean debeCambiarContrasenia;


    public LoginResponseDTO(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public LoginResponseDTO(String token, String username, List<String> roles,
                            Long idUsuario, String nombre, Boolean debeCambiarContrasenia) {
        this.token = token;
        this.username = username;
        this.roles = roles;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.debeCambiarContrasenia = debeCambiarContrasenia;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Boolean getDebeCambiarContrasenia() { return debeCambiarContrasenia; }
    public void setDebeCambiarContrasenia(Boolean debeCambiarContrasenia) { this.debeCambiarContrasenia = debeCambiarContrasenia; }

}
