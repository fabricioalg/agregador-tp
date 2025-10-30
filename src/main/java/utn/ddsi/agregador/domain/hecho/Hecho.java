package utn.ddsi.agregador.domain.hecho;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.domain.entities.condicion.fuentes.Fuente;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "hecho")
public class Hecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHecho;
    //getters y setters
    @Column(name = "titulo")
    private String titulo;
    @Column(name="descripcion",length = 1000)
    private String descripcion;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="nombre", column=@Column(name="categoria_nombre"))
    })
    private Categoria categoria;
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    @Column(name = "fechaCarga", nullable = false)
    private LocalDate fechaDeCarga;
    @OneToOne(cascade = CascadeType.ALL)
    private Fuente origen;
    @Embedded
    private Ubicacion lugarDeOcurrencia;
    @Embedded
    private Etiqueta etiqueta;

    public Hecho(String titulo, String descripcion, Categoria categoria,Ubicacion lugarDeOcurrencia, LocalDate fecha, Fuente fuente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.lugarDeOcurrencia = lugarDeOcurrencia;
        this.fecha = fecha;
        this.fechaDeCarga = LocalDate.now();
        this.etiqueta = null;
        this.origen = fuente;
    }

    public Fuente getFuente() {return origen;}
    public void setFuente(Fuente fuente) {this.origen = fuente;}
}
