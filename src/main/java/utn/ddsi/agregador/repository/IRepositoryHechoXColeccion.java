package utn.ddsi.agregador.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.dto.EstadisticaColeccionHechosXProvinciaDTO;
import utn.ddsi.agregador.dto.HechoFuenteDTO;

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
    @Query("SELECT new utn.ddsi.agregador.dto.EstadisticaColeccionHechosXProvinciaDTO(" + // <--- RUTA COMPLETA
            "u.provincia.nombre, COUNT(h)) " +
            "FROM HechoXColeccion hc " +
            "JOIN hc.hecho h " +
            "JOIN h.ubicacion u " +
            "WHERE hc.coleccion.id_coleccion = :coleccion_Id " +
            "GROUP BY u.provincia.nombre")
    List<EstadisticaColeccionHechosXProvinciaDTO> contarHechosDeColeccionDeProvincia(@Param("coleccion_Id") Long coleccioId);


    @Query("SELECT hc FROM HechoXColeccion hc WHERE hc.coleccion.id_coleccion = :coleccionId")
    List<HechoXColeccion> findByColeccion(Long coleccionId);


    @Query("""
    SELECT new utn.ddsi.agregador.dto.HechoFuenteDTO(
        hxc.hecho.id_hecho,
        hxc.hecho.titulo,
        hxc.hecho.descripcion,
        hxc.hecho.fuente.url
    )
    FROM HechoXColeccion hxc
    WHERE hxc.coleccion.id_coleccion = :idColeccion
""")
    List<HechoFuenteDTO> findHechoFuenteData(@Param("idColeccion") Long idColeccion);

    @Query("""
    SELECT hxc
    FROM HechoXColeccion hxc
    JOIN FETCH hxc.hecho h
    LEFT JOIN FETCH h.categoria
    LEFT JOIN FETCH h.etiqueta
    LEFT JOIN FETCH h.fuente
    LEFT JOIN FETCH h.ubicacion u
    LEFT JOIN FETCH u.provincia
    WHERE hxc.coleccion.id_coleccion = :idCol
""")
    List<HechoXColeccion> findByColeccionOptimizado(Long idCol);
}


