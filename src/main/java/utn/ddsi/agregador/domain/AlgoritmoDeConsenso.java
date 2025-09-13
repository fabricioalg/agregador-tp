package utn.ddsi.agregador.domain;

import java.util.List;

public interface AlgoritmoDeConsenso {
    List<Hecho> aplicar(List<Hecho>hechos,List<Fuente> fuentes);

}
