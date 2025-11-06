package utn.ddsi.agregador.domain.coleccion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.List;
import java.util.stream.Collectors;

public class ConsensoDefault extends AlgoritmoDeConsenso {
    @Override
    public List<Hecho> aplicar(List<HechoXColeccion> hechos, List<Fuente> fuentes){
        return hechos.stream().map(HechoXColeccion::getHecho).collect(Collectors.toList());
    }

}
