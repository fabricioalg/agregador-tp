package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

@Getter
@Setter
@Entity
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private List<Fuente> fuentes;
    private List<Hecho> hechos;
    private String handle;
    private List<InterfaceCondicion> criterioDePertenencia;
    @Transient //para arreglar un error
    private AlgoritmoDeConsenso algoritmoDeConsenso;

    public Coleccion(String titulo, String descripcion, List<Fuente> fuentes) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuentes = fuentes;
        this.hechos = new ArrayList<>();
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
