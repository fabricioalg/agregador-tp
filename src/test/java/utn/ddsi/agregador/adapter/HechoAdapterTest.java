package utn.ddsi.agregador.adapter;

import org.junit.jupiter.api.Test;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.hecho.HechoMultimedia;
import utn.ddsi.agregador.domain.hecho.HechoTexto;
import utn.ddsi.agregador.dto.AdjuntoDTO;
import utn.ddsi.agregador.dto.HechoFuenteDinamicaDTO;
import utn.ddsi.agregador.dto.UbicacionDTO;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HechoAdapterTest {

    private final HechoAdapter adapter = new HechoAdapter();

    @Test
    void testAdaptarHechoConAdjunto_DebeCrearHechoMultimedia() {
        // Arrange
        HechoFuenteDinamicaDTO dto = new HechoFuenteDinamicaDTO();
        dto.setTitulo("Evento con imagen");
        dto.setDescripcion("Descripción del evento");
        dto.setFecha(LocalDate.of(2025, 10, 28));
        dto.setCategoria("Deportes");
        
        UbicacionDTO ubicacionDTO = new UbicacionDTO(-34.6f, -58.4f);
        dto.setUbicacion(ubicacionDTO);
        
        AdjuntoDTO adjuntoDTO = new AdjuntoDTO(1L, "http://example.com/imagen.jpg", "image/jpeg");
        dto.setAdjunto(adjuntoDTO);

        // Act
        Hecho hecho = adapter.adaptarDesdeFuenteDinamica(dto);

        // Assert
        assertNotNull(hecho);
        assertTrue(hecho instanceof HechoMultimedia, "Debe ser HechoMultimedia cuando tiene adjunto");
        
        HechoMultimedia hechoMultimedia = (HechoMultimedia) hecho;
        assertEquals("Evento con imagen", hechoMultimedia.getTitulo());
        assertEquals("Descripción del evento", hechoMultimedia.getDescripcion());
        assertEquals("http://example.com/imagen.jpg", hechoMultimedia.rutaAlContenido);
        assertEquals(LocalDate.of(2025, 10, 28), hechoMultimedia.getFecha());
        assertEquals(LocalDate.now(), hechoMultimedia.getFechaDeCarga());
        assertEquals("Deportes", hechoMultimedia.getCategoria().getNombre());
        assertNotNull(hechoMultimedia.getLugarDeOcurrencia());
        assertEquals(-34.6f, hechoMultimedia.getLugarDeOcurrencia().getLatitud());
        assertEquals(-58.4f, hechoMultimedia.getLugarDeOcurrencia().getLongitud());
    }

    @Test
    void testAdaptarHechoSinAdjunto_DebeCrearHechoTexto() {
        // Arrange
        HechoFuenteDinamicaDTO dto = new HechoFuenteDinamicaDTO();
        dto.setTitulo("Evento de texto");
        dto.setDescripcion("Descripción del evento de texto");
        dto.setFecha(LocalDate.of(2025, 10, 28));
        dto.setCategoria("Cultura");
        
        UbicacionDTO ubicacionDTO = new UbicacionDTO(-34.6f, -58.4f);
        dto.setUbicacion(ubicacionDTO);
        
        dto.setAdjunto(null); // Sin adjunto

        // Act
        Hecho hecho = adapter.adaptarDesdeFuenteDinamica(dto);

        // Assert
        assertNotNull(hecho);
        assertTrue(hecho instanceof HechoTexto, "Debe ser HechoTexto cuando NO tiene adjunto");
        
        HechoTexto hechoTexto = (HechoTexto) hecho;
        assertEquals("Evento de texto", hechoTexto.getTitulo());
        assertEquals("Descripción del evento de texto", hechoTexto.getDescripcion());
        assertEquals("Descripción del evento de texto", hechoTexto.informacion);
        assertEquals(LocalDate.of(2025, 10, 28), hechoTexto.getFecha());
        assertEquals(LocalDate.now(), hechoTexto.getFechaDeCarga());
        assertEquals("Cultura", hechoTexto.getCategoria().getNombre());
    }

    @Test
    void testAdaptarHechoConAdjuntoVacio_DebeCrearHechoTexto() {
        // Arrange
        HechoFuenteDinamicaDTO dto = new HechoFuenteDinamicaDTO();
        dto.setTitulo("Evento con adjunto vacío");
        dto.setDescripcion("Descripción");
        dto.setFecha(LocalDate.of(2025, 10, 28));
        dto.setCategoria("Política");
        
        AdjuntoDTO adjuntoDTO = new AdjuntoDTO(1L, "", ""); // Adjunto con URL vacía
        dto.setAdjunto(adjuntoDTO);

        // Act
        Hecho hecho = adapter.adaptarDesdeFuenteDinamica(dto);

        // Assert
        assertNotNull(hecho);
        assertTrue(hecho instanceof HechoTexto, "Debe ser HechoTexto cuando el adjunto tiene URL vacía");
    }

    @Test
    void testFuenteDinamica() {
        // Arrange
        HechoFuenteDinamicaDTO dto = new HechoFuenteDinamicaDTO();
        dto.setTitulo("Test Fuente");
        dto.setDescripcion("Test");
        dto.setFecha(LocalDate.now());
        dto.setCategoria("Test");

        // Act
        Hecho hecho = adapter.adaptarDesdeFuenteDinamica(dto);

        // Assert
        assertNotNull(hecho.getFuente());
        assertEquals("FuenteDinamica", hecho.getFuente().getNombre());
        assertEquals(EnumTipoFuente.DINAMICA, hecho.getFuente().getTipoFuente());
    }
}
