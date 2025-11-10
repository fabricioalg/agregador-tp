package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MayoriaSimple extends AlgoritmoDeConsenso {
    public boolean aplicar(HechoXColeccion hechoEvaluado, List<HechoXColeccion> hechos, List<Fuente> fuentes) {
        Map<Hecho, Integer> mapa = new HashMap<>();

        // Contar cuántas veces aparece cada hecho
        for (HechoXColeccion h : hechos) {
            mapa.put(h.getHecho(), mapa.getOrDefault(h.getHecho(), 0) + 1);
        }

        // Calcular el mínimo necesario: mayoría simple (más de la mitad)
        int minimo = (int) Math.ceil(fuentes.size() / 2.0);

        // Cuántas veces se menciona el hecho evaluado
        int cantidad = mapa.getOrDefault(hechoEvaluado.getHecho(), 0);

        // Determinar si tiene consenso por mayoría simple
        boolean esConsensuado = cantidad >= minimo;

        return esConsensuado;
    }
}
/*
public List<Hecho> aplicar(List<HechoXColeccion>hechos, List<Fuente> fuentes){
// Mapa para contar las menciones de cada hecho
Map<Hecho, Integer> mapa = new HashMap<>();
List<Hecho> hechosConsensuados = new ArrayList<>();
// Contar menciones
for (HechoXColeccion h : hechos) {
 mapa.put(h.getHecho(), mapa.getOrDefault(h, 0) + 1);
  }
  // Filtrar por al menos la mitad de las fuentes
  for (Map.Entry<Hecho, Integer> entry : mapa.entrySet())
   { if (entry.getValue() > fuentes.size()/2) { hechosConsensuados.add(entry.getKey()); }
   }
   return hechosConsensuados;
    // } */