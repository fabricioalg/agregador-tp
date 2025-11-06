package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.hecho.Hecho;

@Repository
public interface IRepositoryHechos extends JpaRepository<Hecho, Long> {
}
