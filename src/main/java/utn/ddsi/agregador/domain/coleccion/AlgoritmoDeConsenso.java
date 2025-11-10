package utn.ddsi.agregador.domain.coleccion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AlgoritmoDeConsenso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_algoritmo;

    public boolean aplicar(HechoXColeccion hechoEvaluado, List<HechoXColeccion> todos, List<Fuente> fuentes){
        return Boolean.FALSE;
    }

}
