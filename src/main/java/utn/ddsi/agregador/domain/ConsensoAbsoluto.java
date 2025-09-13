package utn.ddsi.agregador.domain;

import ch.qos.logback.core.BasicStatusManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//MODIFICAR ESTO, no son las mismas fuentes del metamapa
public class ConsensoAbsoluto implements AlgoritmoDeConsenso {

    @Override
    public List<Hecho> aplicar(List<Hecho> hechos, List<Fuente> fuentes) {        // Mapa para contar las menciones de cada hecho
        Map<Hecho, Integer> mapa = new HashMap<>();
        List<Hecho> hechosConsensuados = new ArrayList<>();
        for (Hecho h : hechos) {
            mapa.put(h, mapa.getOrDefault(h, 0) + 1);
        }
        // Definir el mínimo: al menos la mitad de las fuentes
        int minimo = (int) Math.ceil(fuentes.size() / 2.0);
            // Filtrar por al menos la mitad de las fuentes
        for (Map.Entry<Hecho, Integer> entry : mapa.entrySet()) {
            if (entry.getValue() > fuentes.size()) {
                hechosConsensuados.add(entry.getKey());
            }
        }
        return hechosConsensuados;
    }
}