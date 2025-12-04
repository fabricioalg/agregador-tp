package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDTO;

import java.util.*;
import java.util.stream.Collectors;

public class MencionesMultiples extends AlgoritmoDeConsenso {
    @Override
    public boolean aplicar(
            List<Fuente> fuentesColeccion,
            HechoFuenteDTO dataFuenteEvaluada,
            List<HechoFuenteDTO> todosLosDatosDeFuentes
    ) {
        System.out.println("dataFuenteEvaluada=" + dataFuenteEvaluada.getIdHecho() + "," + dataFuenteEvaluada.getUrlFuente());
        System.out.println("aplicarDTO: hechoActual=" + dataFuenteEvaluada.getTitulo() + ", todosSize=" + todosLosDatosDeFuentes.size() + ", fuentesSize=" + fuentesColeccion.size());

        // Obtener fuentes que coinciden con el hecho evaluado
        Set<String> fuentesCoincidentes =
                obtenerFuentesCoincidentes(dataFuenteEvaluada, todosLosDatosDeFuentes, fuentesColeccion);

        String tituloEvaluado = dataFuenteEvaluada.getTitulo(); // ahora usamos el DTO

        boolean hayConflicto = todosLosDatosDeFuentes.stream()
                .filter(h -> h.getTitulo().equals(tituloEvaluado))
                .anyMatch(h -> !h.getDescripcion().equals(dataFuenteEvaluada.getDescripcion()));

        if (hayConflicto) return false;

        // Consenso: al menos 2 fuentes deben coincidir
        return fuentesCoincidentes.size() >= 2;
    }
}
