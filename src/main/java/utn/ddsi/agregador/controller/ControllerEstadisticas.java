package utn.ddsi.agregador.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.*;
import utn.ddsi.agregador.service.ServiceEstadisticas;
import utn.ddsi.agregador.service.ServiceHechos;

import java.util.List;
import utn.ddsi.agregador.domain.hecho.Categoria;
import utn.ddsi.agregador.domain.hecho.Ubicacion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/hechos")
public class ControllerEstadisticas {
    private ServiceEstadisticas service;

    public ControllerEstadisticas(ServiceEstadisticas service) {
        this.service = service;
    }

    //De una coleccion, en que provincia se agrupan la mayor cantidad de hechos reportados?
    @GetMapping("/estadisticas/coleccion")                                                               //Antes era id discutir
    public List<EstadisticaColeccionHechosXProvinciaDTO> obtenerCantidadDeHechosXProvincia(@RequestParam String nombreColeccion ){
        
        return this.service.obtenerCantidadHechosDeColeccion(nombreColeccion);
    }

    //Cual es la categoria con mayor cantidad de hechos reportados?
    @GetMapping("estaditicas/categoria")
    public List<EstadisticaCategoriaDTO> obtenerCantidadDeHechosXCategoria() {
        return this.service.obtenerCantidadDeHechosXCategoria();
    }

    //En que provincia se presenta la mayor cantidad de hechos de una cierta categora?
    @GetMapping("estadisticas/categoria")
    public List<EstadisticaProviciaXCategoriaDTO> obtenerCantidadDeHechoXProvinciaXCategoria(@RequestParam String categoria){
       return this.service.obtenerCantidadDeHechoXProvinciaXCategoria(categoria);
    }


    //A que hora del dia ocurren la mayor cantidad de hechos de una cierta categoria?
    @GetMapping("estadisticas/{categoria}")
    public List<EstadisticaCantidadHoraCateDTO> obtenerCantidadDeHechosXHoraXCategoria(@RequestParam String categoria) {
        return this.service.obtenerCantidadDeHechosXHoraXCategoria(categoria);
    }

    //Cuantas solicitudes de eliminacion son spam?
    @GetMapping("estadisticas/solicitudesSpam")
    public EstadisticaSolicitudesDTO obtenerCantidadSpamEnSolicitudes(@RequestParam Long desde){
        return this.service.obtenerCantidadSpamEnSolicitudes(desde);
    }



}
