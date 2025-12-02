package utn.ddsi.agregador.domain.agregador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.domain.condicion.InterfaceCondicion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import utn.ddsi.agregador.repository.IRepositoryHechoXColeccion;
import utn.ddsi.agregador.repository.IRepositoryHechos;

@Component
public class ActualizadorColecciones {

    private List<Loader> loaders;
    private final IRepositoryColecciones repositoryColecciones;
    private final IRepositoryHechos repositoryHechos;
    private final Normalizador normalizador;
    private final GestorDeSolicitudes gestorSolicitudes;
    public final FiltradorDeHechos filtradorDeHechos;
    public final IRepositoryHechoXColeccion repoHechoxColeccion;

    public ActualizadorColecciones(IRepositoryColecciones rcole, IRepositoryHechos rhechos, Normalizador normal, GestorDeSolicitudes gestor, List<Loader> loaders, FiltradorDeHechos filtrador, IRepositoryHechoXColeccion repositoryHechoXColeccion) {
        this.repositoryColecciones = rcole;
        this.repositoryHechos = rhechos;
        this.gestorSolicitudes = gestor;
        this.normalizador = normal;
        this.loaders = loaders;
        this.filtradorDeHechos = filtrador;
        this.repoHechoxColeccion = repositoryHechoXColeccion;
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

    @Transactional
    public void actualizarColecciones(){
        List<Hecho> hechosNuevos = depurarHechos();
        List<Hecho> hechosTotales = repositoryHechos.findAll();
        List<Coleccion> colecciones = repositoryColecciones.findAll();

        // la consigna pide: Este servicio utiliza el mecanismo de rechazos de solicitudes
        // de eliminación spam en forma automática definido en la Entrega 2
        gestorSolicitudes.procesarTodasLasSolicitudes();

        for (Coleccion coleccion : colecciones) {
            List<HechoXColeccion> hechosEnCol = this.repoHechoxColeccion.findByColeccion(coleccion.getId_coleccion());
            List<InterfaceCondicion> condiciones = this.repositoryColecciones.findByIdCondiciones(coleccion.getId_coleccion());
            if(hechosEnCol.isEmpty()) {
                List<Hecho> hechosFiltrados =
                        filtradorDeHechos.devolverHechosAPartirDe(
                                condiciones, hechosTotales
                        );
                for (Hecho h : hechosFiltrados) {
                    HechoXColeccion hxc = new HechoXColeccion(h, coleccion, false);
                    this.repoHechoxColeccion.save(hxc);
                }
            }
            List<Hecho> hechosFiltrados =
                    filtradorDeHechos.devolverHechosAPartirDe(
                            condiciones, hechosNuevos
                    );
            for (Hecho h : hechosFiltrados) {
                HechoXColeccion hxc = new HechoXColeccion(h, coleccion, false);
                this.repoHechoxColeccion.save(hxc);
            }
        }
        repositoryColecciones.saveAll(colecciones);
    }

    @Transactional
    public void ejecutarAlgoritmosDeConsenso() {
        List<Coleccion> colecciones = repositoryColecciones.findAll();
        for (Coleccion coleccion : colecciones) {
            List<HechoXColeccion> hechosEnColeccion = repoHechoxColeccion.findByColeccion(coleccion.getId_coleccion());
            //actualiza las fuentes
            List<Fuente> fuentes = hechosEnColeccion.isEmpty()
                    ? Collections.emptyList()
                    : hechosEnColeccion.stream()
                    .map(hxc -> hxc.getHecho().getFuente())
                    .distinct()
                    .toList();

            coleccion.actualizarFuentes(fuentes);

            for (HechoXColeccion hxc : hechosEnColeccion) {
                coleccion.aplicarConsenso(hxc);
                repoHechoxColeccion.save(hxc);
            }
        }

        repositoryColecciones.saveAll(colecciones);
    }
}
