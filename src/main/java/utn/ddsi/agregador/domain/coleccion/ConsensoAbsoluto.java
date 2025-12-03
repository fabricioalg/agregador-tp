package utn.ddsi.agregador.domain.coleccion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDTO;

import java.util.*;
import java.util.stream.Collectors;

public class ConsensoAbsoluto extends AlgoritmoDeConsenso {
    @Override
    public boolean aplicar(
            HechoXColeccion hechoEvaluado,
            List<HechoXColeccion> todos,
            List<Fuente> fuentesColeccion,
            HechoFuenteDTO dataFuenteEvaluada,
            List<HechoFuenteDTO> todosLosDatosDeFuentes
    ) {
        if (fuentesColeccion == null || fuentesColeccion.size() < 2) {
            return false;
        }

        Set<Fuente> fuentesCoincidentes = obtenerFuentesCoincidentes(
                dataFuenteEvaluada,
                todosLosDatosDeFuentes,
                fuentesColeccion
        );

        return fuentesCoincidentes.size() == fuentesColeccion.size();
    }
}