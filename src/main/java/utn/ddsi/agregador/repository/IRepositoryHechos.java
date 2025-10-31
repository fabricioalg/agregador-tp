package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.ddsi.agregador.domain.hecho.Hecho;

public interface IRepositoryHechos extends JpaRepository<Hecho, Long> {
}
