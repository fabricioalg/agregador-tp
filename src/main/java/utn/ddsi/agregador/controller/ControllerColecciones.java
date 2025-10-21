package utn.ddsi.agregador.controller;

import utn.ddsi.agregador.domain.Coleccion;
import utn.ddsi.agregador.domain.Hecho;
import utn.ddsi.agregador.repository.RepositoryColecciones;
import utn.ddsi.agregador.service.ServiceColecciones;

import java.util.List;
import java.util.Optional;

public class ControllerColecciones {
    private ServiceColecciones service;
    public void cargarColeccionConHechos(String coleccion, List<Hecho> hechos){
        service.cargarColeccionConHechos(coleccion,hechos);
    }
}