package upc.edu.pe.ecorecicla_backend.dtos;

public class CanjePorUsuarioDTO {
    private String nombreUsuario;
    private int cantidadCanjes;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCantidadCanjes() {
        return cantidadCanjes;
    }

    public void setCantidadCanjes(int cantidadCanjes) {
        this.cantidadCanjes = cantidadCanjes;
    }
}
