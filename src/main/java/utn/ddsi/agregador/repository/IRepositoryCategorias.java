package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.hecho.Categoria;

import java.util.List;

@Repository
public interface IRepositoryCategorias extends JpaRepository<Categoria,Long> {
    Categoria findByNombre(String categoria);
    @Query("SELECT DISTINCT c.nombre FROM Cateoria c")
    List<String> obtenerNombreDeCategorias();
}
