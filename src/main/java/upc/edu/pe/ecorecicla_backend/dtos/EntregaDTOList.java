package upc.edu.pe.ecorecicla_backend.dtos;

import java.time.LocalDateTime;

public class EntregaDTOList {

    private Long idEntrega;
    private Double cantidadKg;
    private int puntosGeneredos;
    private LocalDateTime fecha;

    private Long materialIdMaterial;
    private Long centroAcopioIdCentroAcopio;
    private Long usuarioIdUsuario;


    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Double getCantidadKg() {
        return cantidadKg;
    }

    public void setCantidadKg(Double cantidadKg) {
        this.cantidadKg = cantidadKg;
    }

    public int getPuntosGeneredos() {
        return puntosGeneredos;
    }

    public void setPuntosGeneredos(int puntosGeneredos) {
        this.puntosGeneredos = puntosGeneredos;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getMaterialIdMaterial() {
        return materialIdMaterial;
    }

    public void setMaterialIdMaterial(Long materialIdMaterial) {
        this.materialIdMaterial = materialIdMaterial;
    }

    public Long getCentroAcopioIdCentroAcopio() {
        return centroAcopioIdCentroAcopio;
    }

    public void setCentroAcopioIdCentroAcopio(Long centroAcopioIdCentroAcopio) {
        this.centroAcopioIdCentroAcopio = centroAcopioIdCentroAcopio;
    }

    public Long getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(Long usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }
}
