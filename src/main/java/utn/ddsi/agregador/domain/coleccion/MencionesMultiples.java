package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.*;
import java.util.stream.Collectors;

public class MencionesMultiples extends AlgoritmoDeConsenso {
    @Override
    public boolean aplicar(HechoXColeccion hechoEvaluado, List<HechoXColeccion> todos, List<Fuente> fuentes) {
        Set<Fuente> fuentesCoincidentes = obtenerFuentesCoincidentes(hechoEvaluado, todos, fuentes);

        //mismo título, distinto contenido
        boolean hayConflicto = todos.stream()
                .map(HechoXColeccion::getHecho)
                .filter(h -> h.getTitulo().equals(hechoEvaluado.getHecho().getTitulo()))
                .anyMatch(h -> !h.equals(hechoEvaluado.getHecho())); // mismo titulo, distinto objeto
        if (hayConflicto) {
            return false;
        }
        return fuentesCoincidentes.size() >= 2;
    }
}

/*
public boolean aplicar(HechoXColeccion hechoEvaluado, List<HechoXColeccion> hechos, List<Fuente> fuentes) {
        Map<Hecho, Integer> mapa = new HashMap<>();
        // Contar menciones
        for (HechoXColeccion h : hechos) {
            mapa.put(h.getHecho(), mapa.getOrDefault(h.getHecho(), 0) + 1);
        }
        // Contar cuántas veces aparece el hechoEvaluado
        int cantidad = mapa.getOrDefault(hechoEvaluado.getHecho(), 0);
        // Verificar si aparece más de 2 veces
        boolean esConsensuado = cantidad > 2;
        return esConsensuado;
    }
 */