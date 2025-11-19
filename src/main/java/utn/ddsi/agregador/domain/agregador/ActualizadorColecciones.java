package utn.ddsi.agregador.domain.agregador;

import java.time.LocalDate;
import java.util.ArrayList;
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
        List <Hecho> hechosNuevos = new ArrayList();
        for(Loader loader : loaders){
            hechosNuevos.addAll(loader.obtenerHechos()); //Falta poner lo de la horas según corresponda al Loader
        }
        return hechosNuevos;
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

        // la consigna pide: Este servicio utiliza el mecanismo de rechazos de solicitudes
        // de eliminación spam en forma automática definido en la Entrega 2
        gestorSolicitudes.procesarTodasLasSolicitudes();
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
