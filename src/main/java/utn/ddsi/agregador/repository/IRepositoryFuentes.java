package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.ddsi.agregador.domain.fuentes.Fuente;

public interface IRepositoryFuentes extends JpaRepository<Fuente, Long> {

    Fuente findByUrl(String url);
}
