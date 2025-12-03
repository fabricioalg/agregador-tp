package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.*;
import java.util.stream.Collectors;

public class ConsensoAbsoluto extends AlgoritmoDeConsenso {
    @Override
    public boolean aplicar(HechoXColeccion hechoEvaluado, List<HechoXColeccion> todos, List<Fuente> fuentes) {
        if (fuentes == null || fuentes.size() < 2) {
            return false;
        }
        Set<Fuente>fuentesCoincidentes = obtenerFuentesCoincidentes(hechoEvaluado, todos, fuentes);
        return fuentesCoincidentes.size() == fuentes.size();
    }
}
/*
@Override
    public boolean aplicar(HechoXColeccion hechoEvaluado, List<HechoXColeccion> todos, List<Fuente> fuentes) {
        Hecho hecho = hechoEvaluado.getHecho();
        boolean consensuado = false;

        // Contar cuántas veces aparece este hecho en la lista total
        long cantidad = todos.stream()
                .filter(hxc -> hxc.getHecho().equals(hecho))
                .count();

        // Definir el mínimo: al menos la mitad de las fuentes
        int minimo = (int) Math.ceil(fuentes.size() / 2.0);

        // Determinar si es consensuado
        if(minimo > 0) {
            consensuado = cantidad >= minimo;
        }
        return consensuado;
    }
 */