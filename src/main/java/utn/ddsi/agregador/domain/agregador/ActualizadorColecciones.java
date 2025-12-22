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
        List<Hecho> hechosNuevos = new ArrayList<>();
        for(Loader loader : loaders){
            try {
                hechosNuevos.addAll(loader.obtenerHechos()); //Falta poner lo de la horas según corresponda al Loader
            } catch (Exception e) {
                // ignorar loader con error y seguir con los siguientes
            }
            }
        return hechosNuevos;
    }

    public List<Hecho> depurarHechos() {
        List<Hecho> todosLosHechos = traerHechosDeLoaders();
        if (todosLosHechos == null || todosLosHechos.isEmpty()) return Collections.emptyList();
        List<Hecho> hechosNormalizados = normalizador.normalizar(todosLosHechos);

        // guardar solo los nuevos (ejemplo si existe getExternalId)
        List<Hecho> aGuardar = new ArrayList<>();
        for (Hecho h : hechosNormalizados) {
            Hecho hechoguardado =repositoryHechos.findByTitulo(h.getTitulo());
            if (hechoguardado == null ||
                    !hechoguardado.getFuente().getId_fuente().equals(h.getFuente().getId_fuente())) {
                aGuardar.add(h);
            }
        }
        repositoryHechos.saveAll(aGuardar);
        return hechosNormalizados;
    }


    @Transactional
    public void actualizarColecciones(){
        depurarHechos(); //En este punto ya guardé los nuevos porloque en el findAll siguiente los trae
        List<Hecho> hechosTotales = repositoryHechos.findAll();
        List<Coleccion> colecciones = repositoryColecciones.findAll();
        List<Hecho> hechosFiltradosPorFuente;
        List<CondicionFuente> condicionesFuentes;
        List<Fuente> fuentesColeccion;

        // la consigna pide: Este servicio utiliza el mecanismo de rechazos de solicitudes
        // de eliminación spam en forma automática definido en la Entrega 2
        //gestorSolicitudes.procesarTodasLasSolicitudes(); AHORA SE HACE SOLO CON SU PROPIO SCHEDULER

        for (Coleccion coleccion : colecciones) {
            // obtener hechos en la coleccion y condiciones de pertenencia
            List<HechoXColeccion> hechosEnCol = this.repoHechoxColeccion.findByColeccion(coleccion.getId_coleccion());
            List<InterfaceCondicion> condiciones = this.repositoryColecciones.findByIdCondiciones(coleccion.getId_coleccion());

            // obtener fuentes y condiciones de fuentes (una sola vez)
            fuentesColeccion = this.repositoryFuente.findFuentesByColeccion(coleccion.getId_coleccion());
            condicionesFuentes = crearCondicionesDeFuentes(fuentesColeccion);

            // 1) Eliminar hechos que ya no cumplen
            eliminarHechosNoValidos(hechosEnCol, condiciones, condicionesFuentes, hechosTotales);

            // 2) Determinar hechos válidos a partir de todas las fuentes y condiciones
            hechosFiltradosPorFuente = filtradorDeHechos.devovelHechosDeFuentes(hechosTotales, condicionesFuentes);
            List<Hecho> hechosFiltrados = filtradorDeHechos.devolverHechosAPartirDe(condiciones, hechosFiltradosPorFuente);

            // 3) Insertar los que falten (sólo los que no existan)
            for (Hecho h : hechosFiltrados) {
                HechoXColeccion hxc = this.repoHechoxColeccion.findByConjunto(coleccion.getId_coleccion(), h.getId_hecho());
                if (hxc == null) {
                    HechoXColeccion nuevo = new HechoXColeccion(h, coleccion, false);
                    this.repoHechoxColeccion.save(nuevo);
                }
            }
        }

        repositoryColecciones.saveAll(colecciones);
    }

    @Transactional
    public List<CondicionFuente> crearCondicionesDeFuentes(List<Fuente> fuentes){

        List<CondicionFuente> condiciones = new ArrayList<>();
        if(!fuentes.isEmpty()){
            for(Fuente f:fuentes){
                CondicionFuente condicion = new CondicionFuente(f);
                condiciones.add(condicion);
            }
        }
        return condiciones;
    }

    // Nuevo método que elimina de la base los HechoXColeccion que ya no cumplen las condiciones
    private void eliminarHechosNoValidos(List<HechoXColeccion> hechosEnCol, List<InterfaceCondicion> condiciones, List<CondicionFuente> condicionesFuentes, List<Hecho> hechosTotales){
        if(hechosEnCol == null || hechosEnCol.isEmpty()) return;

        // Primero filtrar por fuentes como se hace normalmente
        List<Hecho> hechosFiltradosPorFuente = filtradorDeHechos.devovelHechosDeFuentes(hechosTotales, condicionesFuentes);
        // Luego aplicar las condiciones de pertenencia
        List<Hecho> hechosValidos = filtradorDeHechos.devolverHechosAPartirDe(condiciones, hechosFiltradosPorFuente);
        Set<Long> idsValidos = hechosValidos.stream().map(Hecho::getId_hecho).collect(Collectors.toSet());

        // Iterar sobre una copia para evitar ConcurrentModification
        for(HechoXColeccion hxc : new ArrayList<>(hechosEnCol)){
            if(hxc == null || hxc.getHecho() == null) continue;
            Long idHecho = hxc.getHecho().getId_hecho();
            if(!idsValidos.contains(idHecho)){
                // eliminar de la colección porque ya no cumple
                this.repoHechoxColeccion.delete(hxc);
            }
        }
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
                //repoHechoxColeccion.save(hxc);
                //System.out.println("Después de aplicar consenso: " + hxc.getConsensuado());
            }
        }
        repositoryColecciones.saveAll(colecciones);
    }
}
