package utn.ddsi.agregador.domain.hecho;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.utils.EnumTipoHecho;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "hecho")
public class Hecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hecho;
    //getters y setters
    @Column(name = "titulo")
    private String titulo;
    @Column(name="descripcion",length = 1000)
    private String descripcion;
    @ManyToOne(cascade = CascadeType.ALL)
    private Categoria categoria;
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    @Column(name = "fechaCarga", nullable = false)
    private LocalDate fechaDeCarga;
    @ManyToOne(cascade = CascadeType.ALL)
    private Fuente fuente;
    @ManyToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacion;
    @ManyToOne(cascade = CascadeType.ALL)
    private Etiqueta etiqueta;
    @Enumerated(EnumType.STRING)
    private EnumTipoHecho tipoHecho;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Adjunto> adjuntos;

    public Hecho(String titulo, String descripcion, Categoria categoria,Ubicacion ubicacion, LocalDate fecha, Fuente fuente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.fechaDeCarga = LocalDate.now();
        this.etiqueta = null;
        this.fuente = fuente;
    }

    @Override
    public String toString() {
        return "Hecho{" +
                "titulo='" + titulo + '\'' +
                ", fecha=" + fecha +
                ", fuente=" + (fuente != null ? fuente.getNombre() : "null") +
                '}';
    }
}
