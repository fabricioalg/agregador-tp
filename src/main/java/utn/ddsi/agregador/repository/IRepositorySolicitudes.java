package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.solicitudEliminacion.SolicitudEliminacion;

@Repository
public interface IRepositorySolicitudes extends JpaRepository<SolicitudEliminacion,Long> {
}
