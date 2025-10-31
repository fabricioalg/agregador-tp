package utn.ddsi.agregador.domain.coleccion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import utn.ddsi.agregador.domain.condicion.InterfaceCondicion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name="coleccion")
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String handle;
    @Column(nullable = false)
    private String titulo;
    @Column(length = 1000)
    private String descripcion;
    @ManyToMany
    @JoinTable(
            name = "fuente_x_coleccion",
            joinColumns = @JoinColumn(name = "id_coleccion"),
            inverseJoinColumns = @JoinColumn(name = "id_fuente")
    )
    private List<Fuente> fuentes;
    @ManyToMany
    @JoinTable(
            name = "hecho_x_coleccion",
            joinColumns = @JoinColumn(name = "id_coleccion"),
            inverseJoinColumns = @JoinColumn(name = "id_hecho")
    )
    private List<Hecho> hechos;

    @ManyToMany
    @JoinTable(
            name = "criterio_x_coleccion",
            joinColumns = @JoinColumn(name = "id_coleccion"),
            inverseJoinColumns = @JoinColumn(name = "id_criterio")
    )
    private List<InterfaceCondicion> criterioDePertenencia;
    @ManyToOne //va a quedar asi por un rato
    private AlgoritmoDeConsenso algoritmoDeConsenso;

    public Coleccion(String titulo, String descripcion, List<Fuente> fuentes, String handle) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuentes = fuentes;
        this.hechos = new ArrayList<>();
        this.criterioDePertenencia = new ArrayList<>();
        this.algoritmoDeConsenso = new ConsensoDefault();
    }

    public void setearFuente() {
        this.hechos.forEach((h) -> h.setFuente(this.fuentes.get(0)));
    }
    public List<Hecho> obtenerHechosConsensuados(List<Fuente> fuentes){ //hacer otro map 0 no consensuado 1 consensuado
        List<Hecho> hechosConsensuados=this.algoritmoDeConsenso.aplicar(this.hechos,fuentes);
        return hechosConsensuados;
    }

    public List<Fuente> obtenerFuentes() {
        List<Fuente> fuentes = this.hechos.stream().map(Hecho::getFuente).distinct().collect(Collectors.toList());
        return fuentes;
    }

    public void agregarHechos(List<Hecho> nuevosHechos) {
        hechos.addAll(nuevosHechos);
    }
    public void cambiarCriterioDePertenencia(List<InterfaceCondicion> criterio) {
        criterioDePertenencia = criterio;
    }

}
