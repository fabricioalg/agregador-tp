package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.coleccion.Coleccion;

import java.util.Optional;

@Repository
public interface IRepositoryColecciones extends JpaRepository<Coleccion, Long> {
    Coleccion findByTitulo(String nombre);
}
