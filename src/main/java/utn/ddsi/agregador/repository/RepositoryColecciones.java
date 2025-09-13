package utn.ddsi.agregador.repository;

import utn.ddsi.agregador.domain.Coleccion;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
public class RepositoryColecciones {
    private Map<String, Coleccion> colecciones = new HashMap<>();

    public void save(Coleccion coleccion) {colecciones.put(coleccion.getTitulo(), coleccion);}
    public void delete(Coleccion coleccion){colecciones.remove(coleccion);}
    public Optional<Coleccion> buscarPorNombre(String titulo) {
        return Optional.ofNullable(colecciones.get(titulo));
    }
}
