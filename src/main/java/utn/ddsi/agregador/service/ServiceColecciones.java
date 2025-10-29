package utn.ddsi.agregador.service;

import org.springframework.web.bind.annotation.*;
import utn.ddsi.agregador.domain.*;
import utn.ddsi.agregador.repository.RepositoryColecciones;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceColecciones {
    private RepositoryColecciones repositoryColecciones;

    public void cargarColeccionConHechos(String coleccion, List<Hecho> hechos){
        Optional<Coleccion> coleccionConTitulo = repositoryColecciones.buscarPorNombre(coleccion);
        if(coleccionConTitulo.isPresent()){
            coleccionConTitulo.get().agregarHechos(hechos);
        }
        else{
            throw new RuntimeException("no se encontró la coleccion");
        }
    }
    public void crearColeccion(String titulo, String descripcion, List<Fuente> fuentes, String handle) {
        Coleccion nuevaCole = new Coleccion(titulo,descripcion,fuentes,handle);
        repositoryColecciones.save(nuevaCole);
    }
    public List<Coleccion> obtenerTodasLasColecciones(){
        return repositoryColecciones.obtenerTodasLasColecciones();
    }
    public Optional<Coleccion> buscarPorNombre(String titulo) {
        return repositoryColecciones.buscarPorNombre(titulo);
    }
    public void eliminarColeccion(String titulo) {
        Optional<Coleccion> coleccionAEliminar = repositoryColecciones.buscarPorNombre(titulo);
        coleccionAEliminar.ifPresent(coleccion -> repositoryColecciones.delete(coleccion));
    }

}

