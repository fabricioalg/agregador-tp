package utn.ddsi.agregador.domain.agregador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.*;
import java.util.List;

import org.junit.jupiter.api.Test;

import utn.ddsi.agregador.domain.hecho.Adjunto;
import utn.ddsi.agregador.domain.hecho.Categoria;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.hecho.Provincia;
import utn.ddsi.agregador.domain.hecho.Ubicacion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.utils.EnumTipoFuente;
import utn.ddsi.agregador.utils.TipoMedia;

class NormalizadorTest {

    private final Clock clock = Clock.fixed(Instant.parse("2025-01-10T00:00:00Z"), ZoneOffset.UTC);
    private final Normalizador normalizador = new Normalizador(clock);

    @Test
    void normalizaCategoriaFechasYProvincia() {
        Hecho hecho = new Hecho();
        hecho.setTitulo("  Incendio forestal en cordoba   ");
        hecho.setDescripcion("  foco activo en zona norte   ");
        hecho.setCategoria(new Categoria("Fuego forestal"));
        hecho.setUbicacion(new Ubicacion(-31.4f, -64.2f));
        hecho.setFecha(LocalDate.of(2025, 1, 12));
        hecho.setFechaDeCarga(LocalDateTime.of(2025, 1,9,5,10));
        List<Hecho> normalizados = normalizador.normalizar(List.of(hecho));

        assertEquals(1, normalizados.size());
        Hecho resultado = normalizados.get(0);
        assertEquals("Incendio forestal", resultado.getCategoria().getNombre());
        assertEquals(LocalDate.of(2025, 1, 10), resultado.getFecha());
        assertEquals(LocalDate.of(2025, 1, 10), resultado.getFechaDeCarga());
        assertEquals("Incendio forestal en cordoba", resultado.getTitulo());
        assertNotNull(resultado.getUbicacion());
        assertNotNull(resultado.getUbicacion().getProvincia());
        assertEquals("Cordoba", resultado.getUbicacion().getProvincia().getNombre());
    }

    @Test
    void fusionaDuplicadosPrivilegiandoFuenteMasConfiable() {
        Hecho base = new Hecho();
        base.setTitulo("Incendio en las sierras");
        base.setDescripcion("Foco contenido");
        base.setCategoria(new Categoria("Incendio forestal"));
        base.setUbicacion(new Ubicacion(-31.40f, -64.20f));
        base.setFecha(LocalDate.of(2025, 1, 8));
        base.setFechaDeCarga(LocalDateTime.of(2025, 1, 8,5,10));
        base.setAdjuntos(List.of(new Adjunto(0, TipoMedia.IMAGEN, "https://ejemplo/img.jpg")));
        Fuente fuenteDinamica = new Fuente();
        fuenteDinamica.setNombre("Fuente comunitaria");
        fuenteDinamica.setUrl("https://comunidad");
        fuenteDinamica.setTipoFuente(EnumTipoFuente.DINAMICA);
        base.setFuente(fuenteDinamica);

        Hecho candidato = new Hecho();
        candidato.setTitulo("  incendio en las sierras  ");
        candidato.setDescripcion("Fuego en zona de dificil acceso con 3 brigadas trabajando.");
        candidato.setCategoria(new Categoria("Fuego forestal"));
        candidato.setUbicacion(new Ubicacion(-31.401f, -64.201f));
        candidato.setFecha(LocalDate.of(2025, 1, 9));
        candidato.setFechaDeCarga(LocalDateTime.of(2025, 1, 9,9,10));
        candidato.setAdjuntos(List.of(new Adjunto(0, TipoMedia.VIDEO, "https://ejemplo/video.mp4")));
        Fuente fuenteEstatica = new Fuente();
        fuenteEstatica.setNombre("Base oficial");
        fuenteEstatica.setUrl("https://datos");
        fuenteEstatica.setTipoFuente(EnumTipoFuente.ESTATICA);
        candidato.setFuente(fuenteEstatica);

        List<Hecho> normalizados = normalizador.normalizar(List.of(base, candidato));

        assertEquals(1, normalizados.size());
        Hecho resultado = normalizados.get(0);
        assertEquals("Incendio forestal", resultado.getCategoria().getNombre());
        assertTrue(resultado.getDescripcion().contains("dificil acceso"));
        assertEquals(EnumTipoFuente.ESTATICA, resultado.getFuente().getTipoFuente());
        assertNotNull(resultado.getAdjuntos());
        assertEquals(2, resultado.getAdjuntos().size());
    }

    @Test
    void descartaUbicacionesInvalidas() {
        Hecho hecho = new Hecho();
        hecho.setTitulo("hecho sin ubicacion valida");
        hecho.setCategoria(new Categoria("contaminacion"));
        hecho.setUbicacion(new Ubicacion(120f, -200f));
        hecho.setFecha(LocalDate.of(2025, 1, 5));
        hecho.setFechaDeCarga(LocalDateTime.of(2025, 1, 6,9,10));

        List<Hecho> normalizados = normalizador.normalizar(List.of(hecho));

        assertEquals(1, normalizados.size());
        assertNull(normalizados.get(0).getUbicacion());
    }

    @Test
    void asignaProvinciaDesdePoligonoDelCsv() {
        Hecho hecho = new Hecho();
        hecho.setTitulo("situacion en el microcentro");
        hecho.setCategoria(new Categoria("incendio"));
        hecho.setUbicacion(new Ubicacion(-34.6037f, -58.3816f));
        hecho.setFecha(LocalDate.of(2025, 1, 7));
        hecho.setFechaDeCarga(LocalDateTime.of(2025, 1, 7,9,10));

        List<Hecho> normalizados = normalizador.normalizar(List.of(hecho));

        assertEquals(1, normalizados.size());
        Ubicacion ubicacion = normalizados.get(0).getUbicacion();
        assertNotNull(ubicacion);
        assertNotNull(ubicacion.getProvincia());
        assertEquals("Ciudad Autonoma de Buenos Aires", ubicacion.getProvincia().getNombre());
    }

    @Test
    void reutilizaInstanciaDeProvinciaCacheada() {
        Hecho primero = new Hecho();
        primero.setTitulo("evento en la capital");
        primero.setCategoria(new Categoria("incendio"));
        primero.setUbicacion(new Ubicacion(-34.6037f, -58.3816f));
        primero.setFecha(LocalDate.of(2025, 1, 7));
        primero.setFechaDeCarga(LocalDateTime.of(2025, 1, 7,9,10));

        Hecho segundo = new Hecho();
        segundo.setTitulo("evento en puerto madero");
        segundo.setCategoria(new Categoria("incendio"));
        segundo.setUbicacion(new Ubicacion(-34.6137f, -58.3616f));
        segundo.setFecha(LocalDate.of(2025, 1, 8));
        segundo.setFechaDeCarga(LocalDateTime.of(2025, 1, 8,9,10));

        List<Hecho> normalizados = normalizador.normalizar(List.of(primero, segundo));

        assertEquals(2, normalizados.size());
        Provincia provinciaUno = normalizados.get(0).getUbicacion().getProvincia();
        Provincia provinciaDos = normalizados.get(1).getUbicacion().getProvincia();
        assertNotNull(provinciaUno);
        assertNotNull(provinciaDos);
        assertSame(provinciaUno, provinciaDos);
    }

    @Test
    void ubicaHechosFueraDeArgentinaComoExterior() {
        Hecho hecho = new Hecho();
        hecho.setTitulo("incidente internacional");
        hecho.setCategoria(new Categoria("contaminacion"));
        hecho.setUbicacion(new Ubicacion(40.7128f, -74.0060f));
        hecho.setFecha(LocalDate.of(2025, 1, 4));
        hecho.setFechaDeCarga(LocalDateTime.of(2025, 1, 5,9,10));

        List<Hecho> normalizados = normalizador.normalizar(List.of(hecho));

        assertEquals(1, normalizados.size());
        Ubicacion ubicacion = normalizados.get(0).getUbicacion();
        assertNotNull(ubicacion);
        Provincia provincia = ubicacion.getProvincia();
        assertNotNull(provincia);
        assertEquals("EXTERIOR", provincia.getNombre());
        assertEquals("EXTERIOR", provincia.getPais());
    }
}
