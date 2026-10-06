package upc.edu.pe.ecorecicla_backend.dtos;

public class RecompensaMasCanjeadaDTO {
    private String nombreRecompensa;
    private int totalCanjes;

    public RecompensaMasCanjeadaDTO() {
    }

    public String getNombreRecompensa() {
        return nombreRecompensa;
    }

    public void setNombreRecompensa(String nombreRecompensa) {
        this.nombreRecompensa = nombreRecompensa;
    }

    public int getTotalCanjes() {
        return totalCanjes;
    }

    public void setTotalCanjes(int totalCanjes) {
        this.totalCanjes = totalCanjes;
    }
}

