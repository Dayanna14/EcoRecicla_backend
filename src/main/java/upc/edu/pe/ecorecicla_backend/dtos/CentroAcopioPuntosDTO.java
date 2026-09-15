package upc.edu.pe.ecorecicla_backend.dtos;

public class CentroAcopioPuntosDTO {
    private String nombreCentro;
    private int totalPuntos;

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }
}
