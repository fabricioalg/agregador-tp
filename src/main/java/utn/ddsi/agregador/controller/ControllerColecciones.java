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
    public void cargarColeccionConHechos(String coleccion, List<Hecho> hechos){
        service.cargarColeccionConHechos(coleccion,hechos);
    }

    @PostMapping
    public void crearColeccion(@RequestBody Coleccion coleccion) {
        service.crearColeccion(coleccion.getTitulo(), coleccion.getDescripcion(), coleccion.getFuentes(), coleccion.getHandle());
    }

    @GetMapping
    public List<Coleccion> obtenerTodasLasColecciones() {
        return service.obtenerTodasLasColecciones();
    }

    @GetMapping("/{titulo}")
    public Optional<Coleccion> buscarPorNombre(@RequestParam String titulo) {
        return service.buscarPorNombre(titulo);
    }

    @DeleteMapping("/{titulo}")
    public void eliminarColeccion(@RequestParam String titulo) {
        service.eliminarColeccion(titulo);
    }

}