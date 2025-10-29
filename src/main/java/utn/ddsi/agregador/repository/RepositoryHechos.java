package utn.ddsi.agregador.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.Hecho;

import java.util.*;


@Getter
@Setter
@Repository
public class RepositoryHechos {
    private final Map<Long, Hecho> hechos = new HashMap<>();
    private long nextId = 1;

    public Hecho save(Hecho hecho) {
        hecho.setIdHecho(nextId++);
        hechos.put(hecho.getIdHecho(), hecho);
        return hecho;
    }

    public Optional<Hecho> findById(Long id) {
        return Optional.ofNullable(hechos.get(id));
    }

    public Optional<Hecho> findByTitulo(String titulo) {
        return hechos.values().stream()
                .filter(h -> h.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    public List<Hecho> findAll() {
        return new ArrayList<>(hechos.values());
    }
}
