package utn.ddsi.agregador.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.repository.IRepositoryHechos;
import utn.ddsi.agregador.service.ServiceHechos;

import java.util.List;
import utn.ddsi.agregador.domain.hecho.Categoria;
import utn.ddsi.agregador.domain.hecho.Ubicacion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.utils.EnumTipoFuente;
import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/hechos")
public class ControllerHechos {

    @Autowired
    private  final IRepositoryHechos repoHechos;

    public ControllerHechos(IRepositoryHechos repoHechos) {
        this.repoHechos = repoHechos;
    }






    /*// Buscar por ID
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
    */

}
