package utn.ddsi.agregador.domain.agregador;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Component;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.domain.condicion.CondicionFuente;
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

@Data
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
            try {
                hechosNuevos.addAll(loader.obtenerHechos()); //Falta poner lo de la horas según corresponda al Loader
            } catch (Exception e) {
                continue;
            }
            }
        return hechosNuevos;
    }

    public List<Hecho> depurarHechos() {
        // mi fuente estatica lo manda 2 veces
        List<Hecho> todosLosHechos = traerHechosDeLoaders();
        //los normaliza
        if(todosLosHechos == null || todosLosHechos.isEmpty()) return Collections.emptyList();
        List<Hecho> hechosNormalizados = normalizador.normalizar(todosLosHechos);
        // aca recien los sube y se duplica
        repositoryHechos.saveAll(hechosNormalizados);
        return hechosNormalizados;
    }

    @Transactional
    public void actualizarColecciones(){
        List<Hecho> hechosNuevos = depurarHechos();
        List<Hecho> hechosTotales = repositoryHechos.findAll();
        List<Coleccion> colecciones = repositoryColecciones.findAll();
        List<Hecho> hechosFiltradosPorFuente = new ArrayList<>();
        List<CondicionFuente> condicionesFuentes= new ArrayList<>();
        List<Fuente> fuentesColeccion =new ArrayList<>();

        // la consigna pide: Este servicio utiliza el mecanismo de rechazos de solicitudes
        // de eliminación spam en forma automática definido en la Entrega 2
        //gestorSolicitudes.procesarTodasLasSolicitudes(); AHORA SE HACE SOLO CON SU PROPIO SCHEDULER

        for (Coleccion coleccion : colecciones) {
            List<HechoXColeccion> hechosEnCol = this.repoHechoxColeccion.findByColeccion(coleccion.getId_coleccion());
            List<InterfaceCondicion> condiciones = this.repositoryColecciones.findByIdCondiciones(coleccion.getId_coleccion());
            if(hechosEnCol.isEmpty()) {

                fuentesColeccion= this.repositoryFuente.findFuentesByColeccion(coleccion.getId_coleccion());
                condicionesFuentes =crearCondicionesDeFuentes(fuentesColeccion);
                hechosFiltradosPorFuente = filtradorDeHechos.devovelHechosDeFuentes(hechosTotales,condicionesFuentes);
                //Agrego condicones de fuente en las condciones de pertenencia para obtener el filtrado ocrecto en base a las fuentes de la coleccion
                List<Hecho> hechosFiltrados =
                        filtradorDeHechos.devolverHechosAPartirDe(
                                condiciones, hechosFiltradosPorFuente
                        );
                for (Hecho h : hechosFiltrados) {
                    HechoXColeccion hxc = this.repoHechoxColeccion.findByConjunto(coleccion.getId_coleccion(), h.getId_hecho());
                    if(hxc == null) {
                        HechoXColeccion nuevo = new HechoXColeccion(h, coleccion, false);
                        this.repoHechoxColeccion.save(nuevo);
                    }
                }
            }
            fuentesColeccion= this.repositoryFuente.findFuentesByColeccion(coleccion.getId_coleccion());
            condicionesFuentes =crearCondicionesDeFuentes(fuentesColeccion);
            hechosFiltradosPorFuente = filtradorDeHechos.devovelHechosDeFuentes(hechosTotales,condicionesFuentes);
            List<Hecho> hechosFiltrados =
                    filtradorDeHechos.devolverHechosAPartirDe(
                            condiciones, hechosFiltradosPorFuente
                    );
            for (Hecho h : hechosFiltrados) {
                HechoXColeccion hxc = this.repoHechoxColeccion.findByConjunto(coleccion.getId_coleccion(), h.getId_hecho());
                if(hxc == null) {
                    HechoXColeccion nuevo = new HechoXColeccion(h, coleccion, false);
                    this.repoHechoxColeccion.save(nuevo);
                }
            }
        }
        repositoryColecciones.saveAll(colecciones);
    }

    @Transactional
    public List<CondicionFuente> crearCondicionesDeFuentes(List<Fuente> fuentes){

        List<CondicionFuente> condiciones = new ArrayList<CondicionFuente>();
        if(!fuentes.isEmpty()){
            for(Fuente f:fuentes){
                CondicionFuente condicion = new CondicionFuente(f);
                condiciones.add(condicion);
            }
        }
        return condiciones;
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
            coleccion.setFuentes(fuentes);

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
            //System.out.println("cantidad de hechos " + hechos.size());

            for (HechoXColeccion hxc : hechos) {
                HechoFuenteDTO data = dtoPorHecho.get(hxc.getHecho().getId_hecho());
                //System.out.println("Coleccion " + coleccion.getId_coleccion() + " algoritmo=" + coleccion.getTipoDeAlgoritmo());
                //System.out.println("Hecho=" + hxc.getHecho().getId_hecho() + ", DTO=" + (data != null) + ", totalDTOs=" + datosHechoFuente.size());
                //System.out.println("Fuentes coleccion ids=" + coleccion.getFuentes().stream().map(Fuente::getId_fuente).toList());
                //System.out.println("Antes de guardar: hxc=" + hxc.getId_hecho_x_coleccion() + " cons=" + hxc.getConsensuado());
                coleccion.aplicarConsenso(hxc, data, datosHechoFuente);
                //System.out.println("ANTES SAVE HXC id=" + hxc.getId_hecho_x_coleccion() + " consensuado=" + hxc.getConsensuado());
                repoHechoxColeccion.save(hxc);
                //System.out.println("Después de aplicar consenso: " + hxc.getConsensuado());
            }
        }
        repositoryColecciones.saveAll(colecciones);
    }
}

