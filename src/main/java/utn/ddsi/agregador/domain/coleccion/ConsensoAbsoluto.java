package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//MODIFICAR ESTO, no son las mismas fuentes del metamapa
public class ConsensoAbsoluto extends AlgoritmoDeConsenso {

    @Override
    public boolean aplicar(HechoXColeccion hechoEvaluado, List<HechoXColeccion> todos, List<Fuente> fuentes) {
        Hecho hecho = hechoEvaluado.getHecho();

        // Contar cuántas veces aparece este hecho en la lista total
        long cantidad = todos.stream()
                .filter(hxc -> hxc.getHecho().equals(hecho))
                .count();

        // Definir el mínimo: al menos la mitad de las fuentes
        int minimo = (int) Math.ceil(fuentes.size() / 2.0);

        // Determinar si es consensuado
        boolean consensuado = cantidad >= minimo;

        return consensuado;
    }
}