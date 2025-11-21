package utn.ddsi.agregador.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.hecho.Categoria;
import utn.ddsi.agregador.domain.hecho.Provincia;
import utn.ddsi.agregador.dto.*;
import utn.ddsi.agregador.repository.IRepositoryCategorias;
import utn.ddsi.agregador.repository.IRepositoryHechoXColeccion;
import utn.ddsi.agregador.repository.IRepositoryProvincias;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class ServiceEstadisticas {

    @Autowired
    private ServiceColecciones serviceColecciones;
    private ServiceHechos serviceHechos;
    private IRepositoryHechoXColeccion serviceHechosXColeccion;
    private ServiceSolicitudes serviceSolicitudes;
    private IRepositoryCategorias repoCategorias;
    //private IRepositoryProvincias repoProvincias;
    
    public ServiceEstadisticas(ServiceHechos serviceHechos, ServiceColecciones serviceColecciones,ServiceSolicitudes serviceSolicitudes, IRepositoryCategorias repoCategorias) {
        this.serviceHechos= serviceHechos;
        this.serviceColecciones = serviceColecciones;
        this.serviceSolicitudes = serviceSolicitudes;
        this.repoCategorias = repoCategorias;

    }

    public List<EstadisticaColeccionHechosXProvinciaDTO> obtenerCantidadHechosDeColeccion(String nombreColeccion){
        Coleccion coleccionBuscada = this.serviceColecciones.obtenerPorNombre(nombreColeccion);
        Long id_coleccion = coleccionBuscada.getId_coleccion();
        List<EstadisticaColeccionHechosXProvinciaDTO> estadistica = this.serviceHechosXColeccion.contarHechosDeColeccionDeProvincia(id_coleccion);
        if(estadistica.isEmpty()){
            //generar algo vacio o un error
        }
        return estadistica;
    }

    //Se deberia hacer igual que el anterior
    public List<EstadisticaCategoriaDTO> obtenerCantidadDeHechosXCategoria(){
        List<Categoria> categorias = this.repoCategorias.findAll();
        List<EstadisticaCategoriaDTO> categoriasDatos =  new ArrayList<>();
        for (Categoria categoria : categorias) {
            Long idCategoria = categoria.getId_categoria();
            Long cantidadHechosCategoria = this.serviceHechos.contarHechosDeCategoria(idCategoria);
            EstadisticaCategoriaDTO estadistica_categoria = new EstadisticaCategoriaDTO(cantidadHechosCategoria, categoria.getNombre());
            categoriasDatos.add(estadistica_categoria);
        }
        return categoriasDatos;


    }

    public List<EstadisticaProviciaXCategoriaDTO> obtenerCantidadDeHechoXProvinciaXCategoria(String categoria){
        //TODO
    }

    public List<EstadisticaCantidadHoraCateDTO> obtenerCantidadDeHechosXHoraXCategoria(String categoria){
        //TODO
    }

    public EstadisticaSolicitudesDTO obtenerCantidadSpamEnSolicitudes(Long desde){
        LocalDate desdeLaFecha = LocalDate.now().minusMonths(desde);
        return this.serviceSolicitudes.cantidadSolicitudesSpam(desdeLaFecha);
    }

}