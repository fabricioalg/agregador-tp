package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.condicion.InterfaceCondicion;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRepositoryColecciones extends JpaRepository<Coleccion, Long> {
    Coleccion findByTitulo(String nombre);

    @Query("SELECT DISTINCT c.titulo FROM Coleccion c")
    List<String> obtenerNombresColecciones();

    @Query("SELECT cond FROM Coleccion c JOIN c.condicionDePertenencia cond WHERE c.id = :id")
    List<InterfaceCondicion> findByIdCondiciones(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Coleccion c " +
            "LEFT JOIN FETCH c.hechos hxc " +       // Trae la tabla intermedia
            "LEFT JOIN FETCH hxc.hecho h " +        // Trae los Hechos reales
            "LEFT JOIN FETCH c.fuentes f " +        // Trae las Fuentes
            "LEFT JOIN FETCH c.condicionDePertenencia cond " + // Trae las condiciones
            "WHERE c.id_coleccion = :id")
    Optional<Coleccion> findColeccionCompleta(@Param("id") Long id);
}
