package utn.ddsi.agregador.repository;

import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.Coleccion;

import java.util.*;

@Repository
public class RepositoryColecciones {
    private Map<String, Coleccion> colecciones = new HashMap<>();
    //cambiar titulo por id
    public void save(Coleccion coleccion) {colecciones.put(coleccion.getTitulo(), coleccion);}
    public void delete(Coleccion coleccion){colecciones.remove(coleccion);}
    public Optional<Coleccion> buscarPorNombre(String titulo) {
        return Optional.ofNullable(colecciones.get(titulo));
    }
    public List<Coleccion> obtenerTodasLasColecciones() {
        return new ArrayList<>(colecciones.values());
    }
}
