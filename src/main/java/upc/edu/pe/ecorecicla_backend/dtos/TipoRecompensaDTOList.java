package upc.edu.pe.ecorecicla_backend.dtos;

public class TipoRecompensaDTOList {
    private Long idTipoRecompensa;
    private String nombreTipoRecompensa;
    private String descripcion;

    public Long getIdTipoRecompensa() {
        return idTipoRecompensa;
    }

    public void setIdTipoRecompensa(Long idTipoRecompensa) {
        this.idTipoRecompensa = idTipoRecompensa;
    }

    public String getNombreTipoRecompensa() {
        return nombreTipoRecompensa;
    }

    public void setNombreTipoRecompensa(String nombreTipoRecompensa) {
        this.nombreTipoRecompensa = nombreTipoRecompensa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
