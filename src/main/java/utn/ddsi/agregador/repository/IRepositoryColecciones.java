package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.coleccion.Coleccion;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRepositoryColecciones extends JpaRepository<Coleccion, Long> {
    Coleccion findByTitulo(String nombre);

    @Query("SELECT DISTINCT c.titulo FROM Coleccion c")
    List<String> obtenerNombresColecciones();

}
