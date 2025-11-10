package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.*;

public class MencionesMultiples extends AlgoritmoDeConsenso {
    @Override
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
}

/*
public List<Hecho> aplicar(List<HechoXColeccion>hechos, List<Fuente> fuentes){
// Mapa para contar las menciones de cada hecho
Map<Hecho, Integer> mapa = new HashMap<>();
 List<Hecho> hechosConsensuados = new ArrayList<>();
  // Contar menciones
  for (HechoXColeccion h : hechos){
  mapa.put(h.getHecho(), mapa.getOrDefault(h, 0) + 1);
  }
  // Filtrar por al menos 2 fuentes
  for (Map.Entry<Hecho, Integer> entry : mapa.entrySet())
  { if (entry.getValue() > 2) { hechosConsensuados.add(entry.getKey()); }
  }
  return hechosConsensuados;
  }
 */