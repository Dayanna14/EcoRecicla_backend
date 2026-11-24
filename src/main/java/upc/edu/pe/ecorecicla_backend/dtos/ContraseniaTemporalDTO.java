package upc.edu.pe.ecorecicla_backend.dtos;


// Opción B: el admin restablece la contraseña y se la entrega al usuario
public class ContraseniaTemporalDTO {
    private String nombre;

    private String contraseniaTemporal;

    public ContraseniaTemporalDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseniaTemporal() {
        return contraseniaTemporal;
    }

    public void setContraseniaTemporal(String contraseniaTemporal) {
        this.contraseniaTemporal = contraseniaTemporal;
    }
}
