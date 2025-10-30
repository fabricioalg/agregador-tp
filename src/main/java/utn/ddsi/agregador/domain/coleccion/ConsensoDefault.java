package utn.ddsi.agregador.domain.coleccion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.List;

public class ConsensoDefault implements AlgoritmoDeConsenso {
    @Override
    public List<Hecho> aplicar(List<Hecho> hechos, List<Fuente> fuentes){
        return hechos;
    }

}
