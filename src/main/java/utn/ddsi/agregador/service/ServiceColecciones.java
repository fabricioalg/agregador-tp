package utn.ddsi.agregador.service;

import org.springframework.beans.factory.annotation.Autowired;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceColecciones {
    @Autowired
    private IRepositoryColecciones repositoryColecciones;
    /* PREGUNTA SERIA, ESTO SIRVE? para mi no atte Fabri
    public void cargarColeccionConHechos(Long id, List<Hecho> hechos){
        Optional<Coleccion> coleccionConTitulo = repositoryColecciones.findById(id);
        if(coleccionConTitulo.isPresent()){
            coleccionConTitulo.get().agregarHechos(hechos);
        }
        else{
            throw new RuntimeException("no se encontró la coleccion");
        }
    }*/

    //No se por que est definido asi jaja (att:yeri)
    public List<Coleccion> buscarPorID(Long id) {
        List<Coleccion> colecciones = repositoryColecciones.findAll();
        List<Coleccion> rta = new ArrayList<>();
        colecciones.forEach(coleccion -> {if(coleccion.getId_coleccion().equals(id)) rta.add(coleccion);});
        return rta;
    }
    public Coleccion obtenerPorNombre(String nombre){
        return this.repositoryColecciones.findByTitulo(nombre);
    }

    public List<String> obtenerNombreDeColecciones() {
        return this.repositoryColecciones.obtenerNombresColecciones();
    }
}

