package utn.ddsi.agregador.domain.coleccion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ConsensoDefault extends AlgoritmoDeConsenso {
    @Override
    public boolean aplicar(
            List<Fuente> fuentesColeccion,
            HechoFuenteDTO dataFuenteEvaluada,
            List<HechoFuenteDTO> todosLosDatosDeFuentes
    ) {
        System.out.println("dataFuenteEvaluada=" + dataFuenteEvaluada.getIdHecho() + "," + dataFuenteEvaluada.getUrlFuente());

        System.out.println("aplicarDTO: hechoActual=" + dataFuenteEvaluada.getIdHecho() + ", todosSize=" + todosLosDatosDeFuentes.size() + ", fuentesSize=" + fuentesColeccion.size());
        return true;
    }
}
