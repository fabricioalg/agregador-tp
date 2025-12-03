package utn.ddsi.agregador.domain.coleccion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDTO;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AlgoritmoDeConsenso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_algoritmo;

    public Set<Fuente> obtenerFuentesCoincidentes(
            HechoFuenteDTO hechoActual,
            List<HechoFuenteDTO> todosLosDatos,
            List<Fuente> fuentesColeccion
    ) {
        Long idHecho = hechoActual.getIdHecho();

        List<Long> idsFuentesCoincidentes = todosLosDatos.stream()
                .filter(dto -> dto.getIdHecho().equals(idHecho))
                .map(HechoFuenteDTO::getIdFuente)
                .toList();

        return fuentesColeccion.stream()
                .filter(f -> idsFuentesCoincidentes.contains(f.getId_fuente()))
                .collect(Collectors.toSet());
    }

    public boolean aplicarDTO(
            HechoFuenteDTO hechoActual,
            List<HechoFuenteDTO> todosLosDatosDeFuentes,
            List<Fuente> fuentesColeccion
    ) {
        Set<Fuente> coincidencias =
                obtenerFuentesCoincidentes(hechoActual, todosLosDatosDeFuentes, fuentesColeccion);

        return !coincidencias.isEmpty();
    }

    public boolean aplicar(
            HechoXColeccion hechoEvaluado,
            List<HechoXColeccion> todos,
            List<Fuente> fuentesColeccion,
            HechoFuenteDTO dataFuenteEvaluada,
            List<HechoFuenteDTO> todosLosDatosDeFuentes
    ) {
        return aplicarDTO(dataFuenteEvaluada, todosLosDatosDeFuentes, fuentesColeccion);
    }
}