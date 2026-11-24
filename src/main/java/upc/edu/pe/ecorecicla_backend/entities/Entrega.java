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
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_centro")
    private CentroAcopio centroAcopio;

    // Operador (o admin) que recibió la entrega. Columna simple con el id del usuario:
    // no se usa @ManyToOne para que ModelMapper no confunda este usuario con el reciclador.
    @Column(name = "id_operador")
    private Long idOperador;
    

    public Entrega() {}

    public Entrega(Long idEntrega, Double cantidadKg, int puntosGeneredos, LocalDateTime fecha, Material material, CentroAcopio centroAcopio, Usuarios usuario) {
        this.idEntrega = idEntrega;
        this.cantidadKg = cantidadKg;
        this.puntosGeneredos = puntosGeneredos;
        this.fecha = fecha;
        this.material = material;
        this.centroAcopio = centroAcopio;
        this.usuario = usuario;
    }

    public Long getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Long idOperador) {
        this.idOperador = idOperador;
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

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
