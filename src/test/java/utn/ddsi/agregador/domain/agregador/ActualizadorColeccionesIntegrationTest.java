package utn.ddsi.agregador.domain.agregador;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.agregador.ActualizadorColecciones;
import utn.ddsi.agregador.repository.IRepositoryHechos;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ActualizadorColeccionesIntegrationTest {

    @Autowired
    ActualizadorColecciones actualizador;

    @Test
    void testDepurarHechos() {
        var hechos = actualizador.depurarHechos();
        assertNotNull(hechos);
        assertFalse(hechos.isEmpty());
        assertEquals("Test Titulo", hechos.get(0).getTitulo());
    }
}