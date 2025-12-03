package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDTO;

import java.util.*;
import java.util.stream.Collectors;

public class MencionesMultiples extends AlgoritmoDeConsenso {
    @Override
    public boolean aplicar(
            HechoXColeccion hechoEvaluado,
            List<HechoXColeccion> todos,
            List<Fuente> fuentesColeccion,
            HechoFuenteDTO dataFuenteEvaluada,
            List<HechoFuenteDTO> todosLosDatosDeFuentes
    ) {

        Set<Fuente> fuentesCoincidentes =
                obtenerFuentesCoincidentes(dataFuenteEvaluada, todosLosDatosDeFuentes, fuentesColeccion);

        String tituloEvaluado = hechoEvaluado.getHecho().getTitulo();

        boolean hayConflicto = todos.stream()
                .map(HechoXColeccion::getHecho)
                .filter(h -> h.getTitulo().equals(tituloEvaluado))
                .anyMatch(h -> !h.equals(hechoEvaluado.getHecho()));

        if (hayConflicto) return false;

        return fuentesCoincidentes.size() >= 2;
    }
}
