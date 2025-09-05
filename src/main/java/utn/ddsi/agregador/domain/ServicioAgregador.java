package utn.ddsi.agregador.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utn.ddsi.agregador.repository.RepositoryHechos;

import static java.util.Collections.addAll;

public class ServicioAgregador {
    private List<Loader> loaders;
    private RepositoryHechos repository;
    private Normalizador normalizador;
    private GestorDeSolicitudes gestorSolicitudes;

    public ServicioAgregador() {
        repository = new RepositoryHechos();
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
        repository.saveAll(todosLosHechos);
    }
}
