package upc.edu.pe.ecorecicla_backend.dtos;

public class MaterialMasRecicladoDTO {
    private String nombreMaterial;
    private double totalKgReciclados;
    private int cantidadEntregas;

    public MaterialMasRecicladoDTO() {
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public double getTotalKgReciclados() {
        return totalKgReciclados;
    }

    public void setTotalKgReciclados(double totalKgReciclados) {
        this.totalKgReciclados = totalKgReciclados;
    }

    public int getCantidadEntregas() {
        return cantidadEntregas;
    }

    public void setCantidadEntregas(int cantidadEntregas) {
        this.cantidadEntregas = cantidadEntregas;
    }
}

