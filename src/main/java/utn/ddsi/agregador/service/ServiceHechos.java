package utn.ddsi.agregador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import utn.ddsi.agregador.domain.Coleccion;
import utn.ddsi.agregador.domain.Fuente;
import utn.ddsi.agregador.domain.Hecho;
import utn.ddsi.agregador.repository.RepositoryHechos;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceHechos {
    private final RepositoryHechos repository;

    public ServiceHechos(RepositoryHechos repository) {
        this.repository = repository;
    }

    public Hecho agregarHecho(Hecho hecho) {
        return repository.save(hecho);
    }

    public Optional<Hecho> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Hecho> buscarPorTitulo(String titulo) {
        return repository.findByTitulo(titulo);
    }

    public List<Hecho> obtenerTodos() {
        return repository.findAll();
    }

}
