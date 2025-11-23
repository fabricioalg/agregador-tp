package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.EstadisticaColeccionHechosXProvinciaDTO;

import java.util.List;

@Repository
public interface IRepositoryHechos extends JpaRepository<Hecho, Long> {
    //@Query("SELECT COUNT(*) FROM Hecho h " +
    //        "WHERE h.categoria = id_categoria "
    //)
    //Long contarHechosDeCategoria(@Param("id_categoria") Long idCategoria);

    /*@Query("SELECT (SELECT COUNT(*) FROM Ubicacion ubi JOIN Provincia pro " +
            "ON ubi.id=pro.id " +
            "FROM HECHO a " +
            "JOIN HechoXColeccion hc ON hc.id_coleccion= id_coleccion" +
            "WHERE ")
    */
}