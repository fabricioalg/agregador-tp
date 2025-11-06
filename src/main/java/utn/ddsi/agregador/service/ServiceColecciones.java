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

    public void cargarColeccionConHechos(Long id, List<Hecho> hechos){
        Optional<Coleccion> coleccionConTitulo = repositoryColecciones.findById(id);
        if(coleccionConTitulo.isPresent()){
            coleccionConTitulo.get().agregarHechos(hechos);
        }
        else{
            throw new RuntimeException("no se encontró la coleccion");
        }
    }
    public void crearColeccion(String titulo, String descripcion, List<Fuente> fuentes) {
        Coleccion nuevaCole = new Coleccion(titulo,descripcion,fuentes);
        repositoryColecciones.save(nuevaCole);
    }
    public List<Coleccion> obtenerTodasLasColecciones(){
        return repositoryColecciones.findAll();
    }
    public List<Coleccion> buscarPorNombre(String titulo) {
        List<Coleccion> colecciones = repositoryColecciones.findAll();
        List<Coleccion> rta = new ArrayList<>();
        colecciones.forEach(coleccion -> {if(coleccion.getTitulo().equals(titulo)) rta.add(coleccion);});
        return rta;
    }
    public void eliminarColeccion(Long idColeccion) {
       repositoryColecciones.deleteById(idColeccion);
    }

}

