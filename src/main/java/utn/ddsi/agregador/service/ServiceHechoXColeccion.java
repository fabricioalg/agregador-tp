package utn.ddsi.agregador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.ddsi.agregador.repository.IRepositoryHechoXColeccion;

import java.beans.ConstructorProperties;
//ver si manejar con service o solo con repo

@Service
public class ServiceHechoXColeccion {
    @Autowired
    private IRepositoryHechoXColeccion repohxc;

    public ServiceHechoXColeccion(IRepositoryHechoXColeccion repo){
        this.repohxc=repo;
    }



}
