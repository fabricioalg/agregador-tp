package utn.ddsi.agregador.domain.coleccion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AlgoritmoDeConsenso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_algoritmo;


    public Set<Fuente> obtenerFuentesCoincidentes(HechoXColeccion hechoEvaluado, List<HechoXColeccion> todos, List<Fuente> fuentes) {
        return todos.stream()
                .filter(hxc -> hxc.getHecho().equals(hechoEvaluado.getHecho()))
                .map(hxc -> hxc.getHecho().getFuente())
                .filter(fuentes::contains)
                .collect(Collectors.toSet());
    }
    public boolean aplicar(HechoXColeccion hechoEvaluado, List<HechoXColeccion> todos, List<Fuente> fuentes){
        return Boolean.FALSE;
    }

}
