package utn.ddsi.agregador.adapter;

import org.springframework.stereotype.Component;
import utn.ddsi.agregador.domain.models.fuentes.Fuente;
import utn.ddsi.agregador.domain.models.hecho.*;
import utn.ddsi.agregador.dto.HechoFuenteDinamicaDTO;
import utn.ddsi.agregador.dto.HechoFuenteEstaticaDTO;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

@Component
public class HechoAdapter {

    /**
     * Adapta un HechoFuenteDinamicaDTO (del modelo de la fuente dinámica) al modelo de Hecho del agregador.
     * Si el DTO tiene adjunto, crea un HechoMultimedia, sino crea un HechoTexto.
     */
    public Hecho adaptarDesdeFuenteDinamica(HechoFuenteDinamicaDTO dto) {
        // convierto componentes
        Categoria categoria = new Categoria(dto.getCategoria());
        
        Ubicacion ubicacion = null;
        if (dto.getUbicacion() != null) {
            ubicacion = new Ubicacion(
                dto.getUbicacion().getLatitud(),
                dto.getUbicacion().getLongitud()
            );
        }

        // crear fuente generica para fuente dinamica
        Fuente fuente = crearFuenteDinamica();

        // determino si es multimedia o texto según tenga adjunto
        Hecho hecho;
        if (dto.getAdjunto() != null && dto.getAdjunto().getUrl() != null && !dto.getAdjunto().getUrl().isEmpty()) {
            // Es multimedia
            HechoMultimedia hechoMultimedia = new HechoMultimedia(
                dto.getTitulo(),
                dto.getDescripcion(),
                categoria,
                ubicacion,
                dto.getFecha(),
                fuente
            );
            hechoMultimedia.rutaAlContenido = dto.getAdjunto().getUrl();
            hecho = hechoMultimedia;
        } else {
            // Es texto
            HechoTexto hechoTexto = new HechoTexto(
                dto.getTitulo(),
                dto.getDescripcion(),
                categoria,
                ubicacion,
                dto.getFecha(),
                fuente
            );
            hechoTexto.informacion = dto.getDescripcion();
            hecho = hechoTexto;
        }


        // Establecer la fecha de carga como la fecha actual (cuando se recibe)
        hecho.setFechaDeCarga(LocalDate.now());

        return hecho;
    }

    public Hecho adaptarDesdeFuenteEstatica(HechoFuenteEstaticaDTO dto) {

        Categoria categoria = new Categoria(dto.getCategoria().getNombre());

        Ubicacion ubicacion = null;
        if (dto.getUbicacion() != null) {
            ubicacion = new Ubicacion(
                    dto.getUbicacion().getLatitud(),
                    dto.getUbicacion().getLongitud()
            );
        }

        Fuente fuente = crearFuenteEstatica();
        Hecho hecho = new Hecho(
                dto.getTitulo(),
                dto.getDescripcion(),
                categoria,
                ubicacion,
                dto.getFecha(),
                fuente
        );

        hecho.setFechaDeCarga(LocalDate.now());
        return hecho;
    }

    /**
     * Crea una fuente genérica que representa a la fuente dinámica externa
     */
    private Fuente crearFuenteDinamica() {
        return new FuenteDinamicaExterna(1L, "FuenteDinamica", "http://localhost:8080", EnumTipoFuente.DINAMICA);
    }

    /**
     * Clase interna para representar la fuente dinámica externa
     */
    private static class FuenteDinamicaExterna extends Fuente {
        public FuenteDinamicaExterna(long id, String nombre, String url, EnumTipoFuente tipoFuente) {
            super(id, nombre, url, tipoFuente);
        }
    }

    private Fuente crearFuenteEstatica() {
        return new FuenteEstaticaExterna(2L, "FuenteEstatica", "http://localhost:8082", EnumTipoFuente.ESTATICA);
    }

    private static class FuenteEstaticaExterna extends Fuente {
        public FuenteEstaticaExterna(long id, String nombre, String url, EnumTipoFuente tipoFuente) {
            super(id, nombre, url, tipoFuente);
        }
    }

}
