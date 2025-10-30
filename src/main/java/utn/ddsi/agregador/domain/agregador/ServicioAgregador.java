package utn.ddsi.agregador.domain.agregador;

import java.time.LocalDate;
import java.util.List;

import utn.ddsi.agregador.domain.entities.condicion.coleccion.Coleccion;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.RepositoryColecciones;

public class ServicioAgregador {
    private List<Loader> loaders;
    private RepositoryColecciones repository;
    private Normalizador normalizador;
    private GestorDeSolicitudes gestorSolicitudes;

    public ServicioAgregador() {
        repository = new RepositoryColecciones();
        gestorSolicitudes = new GestorDeSolicitudes();
        normalizador = new Normalizador();
    }
    public void depurarHechos(){
        var hora = LocalDate.now().minusDays(1);
        List<Hecho> todosLosHechos = loaders.stream()
                .flatMap(loader -> loader.obtenerHechos(hora).stream())
                .toList();
        normalizador.normalizar(todosLosHechos);
        gestorSolicitudes.procesarTodasLasSolicitudes();
        agregarHechos("coleccion", todosLosHechos);
    }

    //esto, NO esta bien, tendria que hacerlo por fuente y no por el titulo
    public void agregarHechos(String tituloColeccion, List<Hecho> nuevosHechos) {
        Coleccion coleccion = repository.buscarPorNombre(tituloColeccion)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
        coleccion.agregarHechos(nuevosHechos);
        repository.save(coleccion);
    }
}
