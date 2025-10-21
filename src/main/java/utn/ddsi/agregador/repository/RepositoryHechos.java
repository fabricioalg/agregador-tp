package utn.ddsi.agregador.repository;

import lombok.Getter;
import lombok.Setter;
import utn.ddsi.agregador.domain.Hecho;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Getter
@Setter
public class RepositoryHechos {
    private Map<String, Hecho> hechos = new HashMap<>();

    public void save(Hecho hecho) {hechos.put(hecho.getTitulo(), hecho);}
    public void delete(Hecho hecho){hechos.remove(hecho);}
    public Optional<Hecho> buscarPorNombre(String titulo) {
        return Optional.ofNullable(hechos.get(titulo));
    }
}
