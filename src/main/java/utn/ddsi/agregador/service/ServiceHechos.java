package utn.ddsi.agregador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.repository.IRepositoryHechos;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceHechos {
    
    @Autowired
    private final IRepositoryHechos repository;

    public ServiceHechos(IRepositoryHechos repository) {
        this.repository = repository;
    }


    //public Long contarHechosDeCategoria(Long id_categoria) {
    //   return  this.repository.contarHechosDeCategoria(id_categoria);
    //}


    /*

    public Hecho agregarHecho(Hecho hecho) {
        return repository.save(hecho);
    }

    public Optional<Hecho> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Hecho> buscarPorTitulo(String titulo) {
        List<Hecho> hechos =repository.findAll();
        return hechos.stream()
                .filter(hecho -> hecho.getTitulo().equalsIgnoreCase(titulo))
                .toList();
    }

    public List<Hecho> obtenerTodos() {
        return repository.findAll();
    }

    public void eliminarHecho(Long id) {repository.deleteById(id);}
    */
    //Esto incluso podria ser un id (categoria)

}
