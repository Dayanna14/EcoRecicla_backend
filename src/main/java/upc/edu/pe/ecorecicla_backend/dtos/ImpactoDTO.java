package upc.edu.pe.ecorecicla_backend.dtos;

// Totales de toda la comunidad para la página de inicio (público)
public class ImpactoDTO {
    private Double kgReciclados;
    private Double co2Evitado;
    private Long entregas;
    private Long recicladores;
    private Long centrosActivos;

    public ImpactoDTO() {
    }

    public Double getKgReciclados() {
        return kgReciclados;
    }

    public void setKgReciclados(Double kgReciclados) {
        this.kgReciclados = kgReciclados;
    }

    public Double getCo2Evitado() {
        return co2Evitado;
    }

    public void setCo2Evitado(Double co2Evitado) {
        this.co2Evitado = co2Evitado;
    }

    public Long getEntregas() {
        return entregas;
    }

    public void setEntregas(Long entregas) {
        this.entregas = entregas;
    }

    public Long getRecicladores() {
        return recicladores;
    }

    public void setRecicladores(Long recicladores) {
        this.recicladores = recicladores;
    }

    public Long getCentrosActivos() {
        return centrosActivos;
    }

    public void setCentrosActivos(Long centrosActivos) {
        this.centrosActivos = centrosActivos;
    }
}
