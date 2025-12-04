package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDTO;

import java.util.*;
import java.util.stream.Collectors;

public class ConsensoAbsoluto extends AlgoritmoDeConsenso {
    @Override
    public boolean aplicar(
            List<Fuente> fuentesColeccion,
            HechoFuenteDTO dataFuenteEvaluada,
            List<HechoFuenteDTO> todosLosDatosDeFuentes
    ) {
        System.out.println("dataFuenteEvaluada=" + dataFuenteEvaluada.getIdHecho() + "," + dataFuenteEvaluada.getUrlFuente());
        System.out.println("aplicarDTO: hechoActual=" + dataFuenteEvaluada.getIdHecho() + ", todosSize=" + todosLosDatosDeFuentes.size() + ", fuentesSize=" + fuentesColeccion.size());

        if (fuentesColeccion == null || fuentesColeccion.size() < 2) {
            return false;
        }

        Set<String> fuentesCoincidentes = obtenerFuentesCoincidentes(dataFuenteEvaluada, todosLosDatosDeFuentes, fuentesColeccion);
        System.out.println(
                "[ABSOLUTO] hecho=" + dataFuenteEvaluada.getIdHecho() +
                        " fuentesCoincidentes=" + fuentesCoincidentes.size() +
                        " / fuentesColeccion=" + fuentesColeccion.size() +
                        " → CONSENSO=" + (fuentesCoincidentes.size() == fuentesColeccion.size())
        );

        return fuentesCoincidentes.size() == fuentesColeccion.size();
    }
}