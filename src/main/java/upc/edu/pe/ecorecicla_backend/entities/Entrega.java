package upc.edu.pe.ecorecicla_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrega;
    private Double cantidadKg;
    private int puntosGeneredos;
    private LocalDateTime fecha;


    @ManyToOne
    @JoinColumn(name = "id_material")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_centro")
    private CentroAcopio centroAcopio;
    

    public Entrega() {}

    public Entrega(Long idEntrega, Double cantidadKg, int puntosGeneredos, LocalDateTime fecha, Material material, CentroAcopio centroAcopio, Usuario usuario) {
        this.idEntrega = idEntrega;
        this.cantidadKg = cantidadKg;
        this.puntosGeneredos = puntosGeneredos;
        this.fecha = fecha;
        this.material = material;
        this.centroAcopio = centroAcopio;
        this.usuario = usuario;
    }

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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public CentroAcopio getCentroAcopio() {
        return centroAcopio;
    }

    public void setCentroAcopio(CentroAcopio centroAcopio) {
        this.centroAcopio = centroAcopio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
