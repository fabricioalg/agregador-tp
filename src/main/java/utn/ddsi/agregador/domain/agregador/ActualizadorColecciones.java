package utn.ddsi.agregador.domain.agregador;

import java.time.LocalDate;
import java.util.List;

import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import utn.ddsi.agregador.repository.IRepositoryHechos;

public class ActualizadorColecciones {

    private List<Loader> loaders;
    private IRepositoryColecciones repositoryColecciones;
    private IRepositoryHechos repositoryHechos;
    private Normalizador normalizador;
    private GestorDeSolicitudes gestorSolicitudes;
    public FiltradorDeHechos filtradorDeHechos;

    public ActualizadorColecciones(IRepositoryColecciones rcole, IRepositoryHechos rhechos, Normalizador normal, GestorDeSolicitudes gestor, List<Loader> loaders, FiltradorDeHechos filtrador) {
        this.repositoryColecciones = rcole;
        this.repositoryHechos = rhechos;
        this.gestorSolicitudes = gestor;
        this.normalizador = normal;
        this.loaders = loaders;
        this.filtradorDeHechos = filtrador;
    }
    public List<Hecho> traerHechosDeLoaders(){
        var hora = LocalDate.now().minusDays(1);
        List<Hecho> todosLosHechos = loaders.stream()
                .flatMap(loader -> loader.obtenerHechos(hora).stream())
                .toList();
        return todosLosHechos;
    }
    public List<Hecho> depurarHechos() {
        List<Hecho> todosLosHechos = traerHechosDeLoaders();
        List<Hecho> hechosNormalizados = normalizador.normalizar(todosLosHechos);
        repositoryHechos.saveAll(hechosNormalizados);
        return hechosNormalizados;
    }
    public void actualizarColecciones(){
        List<Hecho> hechosNuevos = depurarHechos();
        List<Coleccion> colecciones = repositoryColecciones.findAll();

        colecciones.forEach(coleccion-> filtradorDeHechos.devolverHechosAPartirDe(
                coleccion.getCondicionDePertenencia(), hechosNuevos));
        repositoryColecciones.saveAll(colecciones);
    }
}
