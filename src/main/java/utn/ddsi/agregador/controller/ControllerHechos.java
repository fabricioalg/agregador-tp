package utn.ddsi.agregador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.service.ServiceHechos;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hechos")
public class ControllerHechos {
    private final ServiceHechos service;

    public ControllerHechos(ServiceHechos service) {
        this.service = service;
    }

    // Crear un Hecho
    @PostMapping
    public ResponseEntity<Hecho> crearHecho(@RequestBody Hecho hecho) {
        Hecho nuevo = service.agregarHecho(hecho);
        return ResponseEntity.ok(nuevo);
    }

    // Buscar por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Hecho> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por título
    @GetMapping("/titulo/{titulo}")
    public List<Hecho> buscarPorTitulo(@PathVariable String titulo) {
        return service.buscarPorTitulo(titulo);
    }

    // Obtener todos los hechos
    @GetMapping
    public List<Hecho> obtenerTodos() {
        return service.obtenerTodos();
    }

    @DeleteMapping
    public void eliminarHecho(@PathVariable Long id) {service.eliminarHecho(id);}
}
