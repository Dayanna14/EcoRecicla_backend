package upc.edu.pe.ecorecicla_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RecompensaListDTO {
    private Long idRecompensa;
    private Long idTipoRecompensa;
    private String nombre;
    private int costoPuntos;
    private boolean estado;
    private String imagenUrl;

    public RecompensaListDTO() {
    }

    public Long getIdRecompensa() {
        return idRecompensa;
    }

    public void setIdRecompensa(Long idRecompensa) {
        this.idRecompensa = idRecompensa;
    }

    public Long getIdTipoRecompensa() {
        return idTipoRecompensa;
    }

    public void setIdTipoRecompensa(Long idTipoRecompensa) {
        this.idTipoRecompensa = idTipoRecompensa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCostoPuntos() {
        return costoPuntos;
    }

    public void setCostoPuntos(int costoPuntos) {
        this.costoPuntos = costoPuntos;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
