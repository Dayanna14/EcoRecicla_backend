package upc.edu.pe.ecorecicla_backend.dtos;

public class UsuarioPorEstadoDTO {
    private String estado;
    private int cantidadUsuarios;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidadUsuarios() {
        return cantidadUsuarios;
    }

    public void setCantidadUsuarios(int cantidadUsuarios) {
        this.cantidadUsuarios = cantidadUsuarios;
    }
}
