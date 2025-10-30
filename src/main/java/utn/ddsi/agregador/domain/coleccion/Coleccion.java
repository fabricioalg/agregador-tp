package utn.ddsi.agregador.domain.coleccion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utn.ddsi.agregador.domain.entities.condicion.InterfaceCondicion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name="coleccion")
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idColeccion;
    @Column(nullable = false)
    private String titulo;
    @Column(length = 1000)
    private String descripcion;
    @OneToMany(mappedBy = "idFuente")
    private List<Fuente> fuentes;
    @OneToMany(mappedBy = "idHecho")
    private List<Hecho> hechos;
    @Column(nullable = false)
    private String handle;
    @Transient
    private List<InterfaceCondicion> criterioDePertenencia;
    @Transient //va a quedar asi por un rato
    private AlgoritmoDeConsenso algoritmoDeConsenso;

    public Coleccion(String titulo, String descripcion, List<Fuente> fuentes, String handle) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuentes = fuentes;
        this.hechos = new ArrayList<>();
        this.criterioDePertenencia = new ArrayList<>();
        this.algoritmoDeConsenso = new ConsensoDefault();
    }
    public Coleccion() {}
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
