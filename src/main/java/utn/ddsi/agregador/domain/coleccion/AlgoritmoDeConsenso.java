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

    public Set<String> obtenerFuentesCoincidentes(
            HechoFuenteDTO hechoActual,
            List<HechoFuenteDTO> todosLosDatos,
            List<Fuente> fuentesColeccion
    ) {
        Set<String> urlsFuentesColeccion = fuentesColeccion.stream()
                .map(Fuente::getUrl)
                .collect(Collectors.toSet());

        return todosLosDatos.stream()
                // por titulo en vez de ID
                .filter(dto -> dto.getTitulo().equals(hechoActual.getTitulo()))
                .map(HechoFuenteDTO::getUrlFuente)
                .filter(urlsFuentesColeccion::contains)
                .collect(Collectors.toSet()); // <-- evita duplicados
    }

    public boolean aplicarDTO(
            HechoFuenteDTO hechoActual,
            List<HechoFuenteDTO> todosLosDatosDeFuentes,
            List<Fuente> fuentesColeccion
    ) {
        System.out.println("aplicarDTO: hechoActual=" + hechoActual
                + ", todosSize=" + todosLosDatosDeFuentes.size()
                + ", fuentesSize=" + fuentesColeccion.size());
        Set<String> coincidencias =
                obtenerFuentesCoincidentes(hechoActual, todosLosDatosDeFuentes, fuentesColeccion);

        return !coincidencias.isEmpty();
    }

    public boolean aplicar(
            List<Fuente> fuentesColeccion,
            HechoFuenteDTO dataFuenteEvaluada,
            List<HechoFuenteDTO> todosLosDatosDeFuentes
    ) {
        return aplicarDTO(dataFuenteEvaluada, todosLosDatosDeFuentes, fuentesColeccion);
    }
}