package utn.ddsi.agregador.domain.agregador;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.domain.condicion.InterfaceCondicion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.dto.HechoFuenteDTO;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import utn.ddsi.agregador.repository.IRepositoryFuentes;
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
    public final IRepositoryFuentes repositoryFuente;

    public ActualizadorColecciones(IRepositoryColecciones rcole, IRepositoryHechos rhechos, Normalizador normal, GestorDeSolicitudes gestor, List<Loader> loaders, FiltradorDeHechos filtrador, IRepositoryHechoXColeccion repositoryHechoXColeccion, IRepositoryFuentes repositoryFuente) {
        this.repositoryColecciones = rcole;
        this.repositoryHechos = rhechos;
        this.gestorSolicitudes = gestor;
        this.normalizador = normal;
        this.loaders = loaders;
        this.filtradorDeHechos = filtrador;
        this.repoHechoxColeccion = repositoryHechoXColeccion;
        this.repositoryFuente = repositoryFuente;
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
/*
    @Transactional
    public void ejecutarAlgoritmosDeConsenso() {
        List<Coleccion> colecciones = repositoryColecciones.findAll();
        for (Coleccion coleccion : colecciones) {
            List<Fuente> fuentes = repositoryFuente.findFuentesByColeccion(coleccion.getId_coleccion());
            coleccion.actualizarFuentes(fuentes);
            List<HechoXColeccion> hechosEnColeccion = repoHechoxColeccion.findHechosByColeccionId(coleccion.getId_coleccion());
            for (HechoXColeccion hxc : hechosEnColeccion) {
                coleccion.aplicarConsenso(hxc, hechosEnColeccion);
                repoHechoxColeccion.save(hxc);
            }
        }
        repositoryColecciones.saveAll(colecciones);
    }*/
    @Transactional
    public void ejecutarAlgoritmosDeConsenso() {
        List<Coleccion> colecciones = repositoryColecciones.findAll();
        for (Coleccion coleccion : colecciones) {
            List<Fuente> fuentes = repositoryFuente.findFuentesByColeccion(coleccion.getId_coleccion());
            coleccion.actualizarFuentes(fuentes);

            List<HechoFuenteDTO> datosHechoFuente =
                    repoHechoxColeccion.findHechoFuenteData(coleccion.getId_coleccion());

            Map<Long, HechoFuenteDTO> dtoPorHecho =
                    datosHechoFuente.stream()
                            .collect(Collectors.toMap(
                                    HechoFuenteDTO::getIdHecho,
                                    d -> d,
                                    (a, b) -> a
                            ));

            List<HechoXColeccion> hechos =
                    repoHechoxColeccion.findByColeccionOptimizado(coleccion.getId_coleccion());
            System.out.println("cantidad de hechos " + hechos.size());

            for (HechoXColeccion hxc : hechos) {
                HechoFuenteDTO data = dtoPorHecho.get(hxc.getHecho().getId_hecho());
                System.out.println("Coleccion " + coleccion.getId_coleccion() + " algoritmo=" + coleccion.getTipoDeAlgoritmo());
                System.out.println("Hecho=" + hxc.getHecho().getId_hecho() + ", DTO=" + (data != null) + ", totalDTOs=" + datosHechoFuente.size());
                System.out.println("Fuentes coleccion ids=" + coleccion.getFuentes().stream().map(Fuente::getId_fuente).toList());
                System.out.println("Antes de guardar: hxc=" + hxc.getId_hecho_x_coleccion() + " cons=" + hxc.getConsensuado());
                coleccion.aplicarConsenso(hxc, data, datosHechoFuente);
                System.out.println("ANTES SAVE HXC id=" + hxc.getId_hecho_x_coleccion() + " consensuado=" + hxc.getConsensuado());
                repoHechoxColeccion.save(hxc);
                System.out.println("Después de aplicar consenso: " + hxc.getConsensuado());
            }
        }
        repositoryColecciones.saveAll(colecciones);
    }

}

