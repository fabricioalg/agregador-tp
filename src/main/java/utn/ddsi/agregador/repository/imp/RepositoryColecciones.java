package utn.ddsi.agregador.repository.imp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.coleccion.Coleccion;

@Repository
public class RepositoryColecciones extends JpaRepository<Coleccion, Long> {
}
