package utn.ddsi.agregador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.EstadisticaCantidadHoraCateDTO;
import utn.ddsi.agregador.dto.EstadisticaCategoriaDTO;
import utn.ddsi.agregador.dto.EstadisticaProviciaXCategoriaDTO;
import utn.ddsi.agregador.repository.IRepositoryHechos;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceHechos {
    
    @Autowired
    private final IRepositoryHechos repositoryHechos;

    public ServiceHechos(IRepositoryHechos serviceHecho) {
        this.repositoryHechos = serviceHecho;
    }

    public Long contarHechosDeCategoria(Long id_categoria) {
       return  this.repositoryHechos.contarHechosDeCategoria(id_categoria);
    }

    public List<EstadisticaCategoriaDTO> contarHechosDeCategorias(){
        return this.repositoryHechos.contarHechosDeCategorias();
    }

    public List<EstadisticaProviciaXCategoriaDTO> obtenerCantidadDeHechosXProvinciaXCategoria(Long categoria) {
        return this.repositoryHechos.obtenerCantidadDeHechosXProvinciaXCategoria(categoria);
    }

    public EstadisticaCantidadHoraCateDTO obtenerCantidadDeHechosXDiaXCategoria(Long categoria) {
        return this.repositoryHechos.obtenerCantidadDeHechosXDiaXCategoria(categoria);
    }

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
