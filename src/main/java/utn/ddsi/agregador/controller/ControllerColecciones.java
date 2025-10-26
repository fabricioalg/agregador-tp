package utn.ddsi.agregador.controller;

import utn.ddsi.agregador.domain.Coleccion;
import utn.ddsi.agregador.domain.Hecho;
import utn.ddsi.agregador.repository.RepositoryColecciones;
import utn.ddsi.agregador.service.ServiceColecciones;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colecciones")
public class ControllerColecciones {
    private ServiceColecciones service;
    public void cargarColeccionConHechos(String coleccion, List<Hecho> hechos){
        service.cargarColeccionConHechos(coleccion,hechos);
    }

    @PostMapping
    public void crearColeccion(@RequestBody Coleccion coleccion) {
        service.crearColeccion(coleccion.getTitulo(), coleccion.getDescripcion(), coleccion.getFuentes());
    }

    @GetMapping
    public List<Coleccion> obtenerTodasLasColecciones() {
        return service.obtenerTodasLasColecciones();
    }

    @GetMapping("/{titulo}")
    public Optional<Coleccion> buscarPorNombre(String titulo) {
        return service.buscarPorNombre(titulo);
    }

    @DeleteMapping("/{titulo}")
    public void eliminarColeccion(@PathVariable String titulo) {
        service.eliminarColeccion(titulo);
    }

}