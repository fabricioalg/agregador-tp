package utn.ddsi.agregador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.service.ServiceColecciones;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colecciones")
public class ControllerColecciones {
    @Autowired
    private ServiceColecciones service;
    @PutMapping
    public void cargarColeccionConHechos(Long id, List<Hecho> hechos){
        service.cargarColeccionConHechos(id,hechos);
    }

    /*
    @PostMapping
    public void crearColeccion(@RequestBody Coleccion coleccion) {
        service.crearColeccion(coleccion.getTitulo(), coleccion.getDescripcion(), coleccion.getFuentes());
    }
    */
    @GetMapping("/{id}")
    public List<Coleccion> buscarPorId(@PathVariable Long id) {
        return service.buscarPorID(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarColeccion(@RequestParam Long idColeccion) {
        service.eliminarColeccion(idColeccion);
    }

}