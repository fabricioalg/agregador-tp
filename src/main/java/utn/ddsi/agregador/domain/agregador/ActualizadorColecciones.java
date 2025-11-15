package utn.ddsi.agregador.domain.agregador;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import utn.ddsi.agregador.repository.IRepositoryHechos;

@Component
public class ActualizadorColecciones {

    private List<Loader> loaders;
    private final IRepositoryColecciones repositoryColecciones;
    private final IRepositoryHechos repositoryHechos;
    private final Normalizador normalizador;
    private final GestorDeSolicitudes gestorSolicitudes;
    public final FiltradorDeHechos filtradorDeHechos;

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
        return loaders.stream()
                .flatMap(loader -> loader.obtenerHechos(hora).stream())
                .toList();
    }
    public List<Hecho> depurarHechos() {
        List<Hecho> todosLosHechos = traerHechosDeLoaders();
        List<Hecho> hechosNormalizados = normalizador.normalizar(todosLosHechos);
        repositoryHechos.saveAll(hechosNormalizados);
        return hechosNormalizados;
    }
    public void actualizarColecciones(){
        List<Hecho> hechosNuevos = depurarHechos();
        List<Hecho> hechosTotales = repositoryHechos.findAll();
        List<Coleccion> colecciones = repositoryColecciones.findAll();
        for (Coleccion coleccion : colecciones) {
            //OJO que puede ser que este vacio pero no necesariamente es nueva la coleccion
            if(coleccion.getHechos() == null){
                List<Hecho> hechosFiltrados =
                        filtradorDeHechos.devolverHechosAPartirDe(
                                coleccion.getCondicionDePertenencia(), hechosTotales
                        );
                coleccion.agregarHechos(hechosFiltrados);
            }
            List<Hecho> hechosFiltrados =
                    filtradorDeHechos.devolverHechosAPartirDe(
                            coleccion.getCondicionDePertenencia(), hechosNuevos
                    );
            coleccion.agregarHechos(hechosFiltrados);
        }
        repositoryColecciones.saveAll(colecciones);
    }
}
