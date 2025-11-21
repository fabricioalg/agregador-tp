package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.hecho.Categoria;

@Repository
public interface IRepositoryCategorias extends JpaRepository<Categoria,Long> {
}
