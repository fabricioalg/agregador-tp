package utn.ddsi.agregador.service;

import utn.ddsi.agregador.domain.Coleccion;
import utn.ddsi.agregador.domain.Hecho;
import utn.ddsi.agregador.repository.RepositoryColecciones;

import java.util.List;
import java.util.Optional;

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
}

