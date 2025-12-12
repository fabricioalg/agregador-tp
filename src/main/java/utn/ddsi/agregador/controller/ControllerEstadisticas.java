package utn.ddsi.agregador.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
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
@RequestMapping("/estadisticas")
public class ControllerEstadisticas {
    @Autowired
    private ServiceEstadisticas service;

    public ControllerEstadisticas(ServiceEstadisticas service) {
        this.service = service;
    }

    //De una coleccion, en que provincia se agrupan la mayor cantidad de hechos reportados?
    @GetMapping("/provinciaxcol")                                                               //Antes era id discutir
    public List<EstadisticaColeccionHechosXProvinciaDTO> obtenerCantidadDeHechosXProvincia
    (@RequestParam (value="coleccion") String nombreColeccion){
       return this.service.obtenerCantidadHechosDeColeccion(nombreColeccion);
    }

    //Cual es la categoria con mayor cantidad de hechos reportados?
    @GetMapping("/categoria")
    public List<EstadisticaCategoriaDTO> obtenerCantidadDeHechosXCategoria() {
        return this.service.obtenerCantidadDeHechosXCategoria();
    }

    //En que provincia se presenta la mayor cantidad de hechos de una cierta categora?
    @GetMapping("/provinciaxcat")
    public List<EstadisticaProviciaXCategoriaDTO> obtenerCantidadDeHechoXProvinciaXCategoria(
            @RequestParam (value = "categoria") String categoria){
       return this.service.obtenerCantidadDeHechoXProvinciaXCategoria(categoria);
    }


    //Chequeado
    //A que hora del dia ocurren la mayor cantidad de hechos de una cierta categoria?
    @GetMapping("/hora")
    public List<EstadisticaCantidadHoraCateDTO > obtenerCantidadDeHechosXHoraXCategoria(
            @RequestParam (value = "categoria")String categoria) {
        return this.service.obtenerCantidadDeHechosXHoraXCategoria(categoria);
    }
    //Chequeado
    //Cuantas solicitudes de eliminacion son spam?
    @GetMapping("/solicitudesSpam")
    public EstadisticaSolicitudesDTO obtenerCantidadSpamEnSolicitudes(){
        return this.service.obtenerCantidadSpamEnSolicitudes();
    }

    //--------------------OBTENER NOMBRES -------------------------
    @GetMapping("/colecciones/nombre") //MEJORAR
    public List<String> obtenerColecciones(){
        return this.service.obtenerNombreColecciones();
    }

    @GetMapping("/provincias/nombre")
    public List<String> obtenerProvincias(){
        return this.service.obtenerNombreProvincias();
    }

    @GetMapping("/categorias/nombre")
    public List<String> obtnerCategorias(){
        return this.service.obtenerNombreCategorias();
    }

}
