package utn.ddsi.agregador.repository;

import utn.ddsi.agregador.domain.Coleccion;
import utn.ddsi.agregador.domain.Hecho;

import java.util.ArrayList;
import java.util.List;

public class RepositoryColecciones {
    private List<Coleccion> colecciones;

    public RepositoryColecciones() {
        this.colecciones = new ArrayList<Coleccion>();
    }

    public void save(Coleccion coleccion) {colecciones.add(coleccion);}
    public void saveAll(List<Coleccion> newColecciones) {colecciones.addAll(newColecciones);}
    public void delete(Coleccion coleccion){colecciones.remove(coleccion);}
}
