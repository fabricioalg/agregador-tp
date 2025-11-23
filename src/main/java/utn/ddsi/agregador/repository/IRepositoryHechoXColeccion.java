package utn.ddsi.agregador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.dto.EstadisticaColeccionHechosXProvinciaDTO;

import java.util.List;

@Repository
public interface IRepositoryHechoXColeccion extends JpaRepository<HechoXColeccion,Long> {

    /*@Query("SELECT  pro.nombre ,count(distinct h.id_hecho)  FROM HechoXColeccion hc " +
            "JOIN Hecho h" +
            "JOIN Ubicacion u" +
            "JOIN Provincia pro" +
            "where hc.id_coleccion = coleccion_Id" +
            "group by pro.nombre ")
    */
    /*@Query("SELECT new utn.ddsi.agregador.dto.EstadisticaColeccionHechosXProvinciaDTO(" + // <--- RUTA COMPLETA
            "u.provincia.nombre, COUNT(h)) " +
            "FROM HechoXColeccion hc " +
            "JOIN hc.hecho h " +
            "JOIN h.ubicacion u " +
            "WHERE hc.coleccion.id_coleccion = :coleccionId " +
            "GROUP BY u.provincia.nombre")
    List<EstadisticaColeccionHechosXProvinciaDTO> contarHechosDeColeccionDeProvincia(@Param("coleccion_Id") Long coleccioId);
*/}


