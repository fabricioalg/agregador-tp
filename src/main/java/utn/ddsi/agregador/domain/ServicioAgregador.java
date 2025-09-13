package utn.ddsi.agregador.domain;

import java.time.LocalDate;
import java.util.List;
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
        //insertarlos a las colecciones? 
        //repository.saveAll(todosLosHechos);
    }
}
