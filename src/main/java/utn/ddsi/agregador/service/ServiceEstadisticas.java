package utn.ddsi.agregador.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.hecho.Categoria;
import utn.ddsi.agregador.dto.*;
import utn.ddsi.agregador.repository.IRepositoryCategorias;
import utn.ddsi.agregador.repository.IRepositoryHechoXColeccion;

import java.util.List;

@Service
public class ServiceEstadisticas {

    @Autowired
    private ServiceColecciones serviceColecciones;
    private ServiceHechos serviceHechos;
    private IRepositoryHechoXColeccion repoHechosXColeccion;
    private ServiceSolicitudes serviceSolicitudes;
    private IRepositoryCategorias repoCategorias;
    //private IRepositoryProvincias repoProvincias;

    public ServiceEstadisticas(ServiceColecciones serviceColecciones, ServiceHechos serviceHechos, IRepositoryHechoXColeccion serviceHechosXColeccion, ServiceSolicitudes serviceSolicitudes,IRepositoryCategorias repoCategorias ){
        this.serviceColecciones = serviceColecciones;
        this.serviceHechos = serviceHechos;
        this.repoHechosXColeccion = serviceHechosXColeccion;
        this.serviceSolicitudes = serviceSolicitudes;
        this.repoCategorias = repoCategorias;
    }

    //De una coleccion, en que provincia se agrupan la mayor cantidad de hechos reportados?
    public List<EstadisticaColeccionHechosXProvinciaDTO> obtenerCantidadHechosDeColeccion(String nombreColeccion){
        Coleccion coleccionBuscada = this.serviceColecciones.obtenerPorNombre(nombreColeccion);
        Long id_coleccion = coleccionBuscada.getId_coleccion();
        List<EstadisticaColeccionHechosXProvinciaDTO> estadistica = this.repoHechosXColeccion.contarHechosDeColeccionDeProvincia(id_coleccion);
        if(estadistica.isEmpty()){
            //generar algo vacio o un error
        }
        return null;
    }

    //Se deberia hacer igual que el anterior
    //Cual es la categoria con mayor cantidad de hechos reportados?
    public List<EstadisticaCategoriaDTO> obtenerCantidadDeHechosXCategoria(){
        return this.serviceHechos.contarHechosDeCategorias();
        //List<> categorias = this.repoCategorias.findAll();
//        List<EstadisticaCategoriaDTO> categoriasDatos =  new ArrayList<>();
//        for (Categoria categoria : categorias) {
//            Long idCategoria = categoria.getId_categoria();
//            Long cantidadHechosCategoria = this.serviceHechos.contarHechosDeCategoria(idCategoria);
//            EstadisticaCategoriaDTO estadistica_categoria = new EstadisticaCategoriaDTO(cantidadHechosCategoria, categoria.getNombre());
//            categoriasDatos.add(estadistica_categoria);
//        }
       // return categorias;


    }

    //En que provincia se presenta la mayor cantidad de hechos de una cierta categoria?
    public List<EstadisticaProviciaXCategoriaDTO> obtenerCantidadDeHechoXProvinciaXCategoria(String categoria){
        Categoria cate = this.repoCategorias.findByNombre(categoria);
        return this.serviceHechos.obtenerCantidadDeHechosXProvinciaXCategoria(cate.getId_categoria());
    }

    //A que hora del dia ocurren la mayor cantidad de hechos de una cierta categoria?
    public List<EstadisticaCantidadHoraCateDTO>  obtenerCantidadDeHechosXHoraXCategoria(String categoria){
        Categoria cate = this.repoCategorias.findByNombre(categoria);
        return this.serviceHechos.obtenerCantidadDeHechosXDiaXCategoria(cate.getId_categoria());
    }

    public EstadisticaSolicitudesDTO obtenerCantidadSpamEnSolicitudes(){
       // LocalDate desdeLaFecha = LocalDate.now().minusMonths(desde);
        return this.serviceSolicitudes.cantidadSolicitudesSpam();
    }

}