package utn.ddsi.agregador.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.service.ServiceColecciones;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/colecciones")
public class ControllerColecciones {
    @Autowired
    private ServiceColecciones service;
    /*
    @PutMapping este me parece que nao sirve
    public void cargarColeccionConHechos(Long id, List<Hecho> hechos){
        service.cargarColeccionConHechos(id,hechos);
    }

    @PostMapping
    public void crearColeccion(@RequestBody Coleccion coleccion) {
        service.crearColeccion(coleccion.getTitulo(), coleccion.getDescripcion(), coleccion.getFuentes());
    }
    */
    @GetMapping("/{id}")
    public List<Coleccion> buscarPorId(@PathVariable Long id) {
        return service.buscarPorID(id);
    }

    @PostMapping
    public ResponseEntity.BodyBuilder agregarColeccion(@RequestBody Coleccion coleccion) {
        log.info("CONTROLLER : Iniciando peticion de Agregar una nueva coleccion");
        // Podria hacer mas validaciones en el futuro
        log.debug("CONTROLLER : Se valida si la peticion sea concordante");
        if (coleccion == null) {
            log.error("Error Técnico: El objeto de la petición es NULL");
            return ResponseEntity.badRequest();
        } else {
            log.info("CONTROLLER : Procesando dato válido: {}", coleccion);
            try {
                this.service.agregar(coleccion);
            } catch (Exception e) {
                log.error("ERROR TÉCNICO: Fallo al intentar agregar la colección. Detalle: {}", e.getMessage(), e);
                return ResponseEntity.internalServerError();
            }
        }
        log.info("CONTROLLER : Se realizo con exito AGREGAR COLECCION");
        return ResponseEntity.ok();
    }

}