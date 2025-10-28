package utn.ddsi.agregador.adapter;

import org.springframework.stereotype.Component;
import utn.ddsi.agregador.domain.*;
import utn.ddsi.agregador.dto.HechoFuenteDinamicaDTO;

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

    /**
     * Crea una fuente genérica que representa a la fuente dinámica externa
     */
    private Fuente crearFuenteDinamica() {
        try {
            URL urlFuenteDinamica = new URL("http://localhost:8080"); // URL de la fuente dinámica
            return new FuenteDinamicaExterna(1L, "FuenteDinamica", urlFuenteDinamica, EnumTipoFuente.DINAMICA);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al crear URL de fuente dinámica", e);
        }
    }

    /**
     * Clase interna para representar la fuente dinámica externa
     */
    private static class FuenteDinamicaExterna extends Fuente {
        public FuenteDinamicaExterna(long id, String nombre, URL url, EnumTipoFuente tipoFuente) {
            super(id, nombre, url, tipoFuente);
        }
    }
}
