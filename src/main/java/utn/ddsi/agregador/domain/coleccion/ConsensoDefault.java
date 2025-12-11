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
        return true;
    }
}
