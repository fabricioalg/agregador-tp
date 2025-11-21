package utn.ddsi.agregador.domain.agregador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.coleccion.ConsensoDefault;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.domain.condicion.InterfaceCondicion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import utn.ddsi.agregador.repository.IRepositoryHechos;
import utn.ddsi.agregador.utils.EnumTipoDeAlgoritmo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActualizadorColeccionesTest {

    @Mock private IRepositoryColecciones repositoryColecciones;
    @Mock private IRepositoryHechos repositoryHechos;
    @Mock private Normalizador normalizador;
    @Mock private GestorDeSolicitudes gestorSolicitudes;
    @Mock private FiltradorDeHechos filtrador;
    @Mock private Loader loader;

    @InjectMocks
    private ActualizadorColecciones actualizador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        actualizador = new ActualizadorColecciones(
                repositoryColecciones,
                repositoryHechos,
                normalizador,
                gestorSolicitudes,
                List.of(loader),   // ✅ ahora no es null
                filtrador
        );
    }

    @Test
    void deberiaActualizarColeccionesConHechosNormalizados() {
        Hecho hecho1 = new Hecho();
        hecho1.setTitulo("Titulo 1");

        Hecho hecho2 = new Hecho();
        hecho2.setTitulo("Titulo 2");

        List<Hecho> hechos = List.of(hecho1, hecho2);

        actualizador = spy(new ActualizadorColecciones(
                repositoryColecciones,
                repositoryHechos,
                normalizador,
                gestorSolicitudes,
                List.of(loader),
                filtrador
        ));

        // Mock del metodo depurarHechos()
        doReturn(hechos).when(actualizador).depurarHechos();

        // Condición
        InterfaceCondicion condicionMock = mock(InterfaceCondicion.class);
        lenient().when(condicionMock.cumpleCondicion(any())).thenReturn(true);

        // Filtrador
        lenient().when(filtrador.devolverHechosAPartirDe(any(), any()))
                .thenReturn(hechos);

        // Colección
        Coleccion coleccion = new Coleccion("titulo");
        coleccion.setCondicionDePertenencia(List.of(condicionMock));
        when(repositoryColecciones.findAll()).thenReturn(List.of(coleccion));

        // WHEN
        actualizador.actualizarColecciones();

        // THEN
        verify(repositoryColecciones).saveAll(List.of(coleccion));

        List<HechoXColeccion> hechosXC = coleccion.getHechos();

        assertNotNull(hechosXC);
        assertEquals(2, hechosXC.size());

        for (HechoXColeccion hxc : hechosXC) {
            assertNotNull(hxc.getHecho());
            assertEquals(coleccion, hxc.getColeccion());
        }
    }
    @Test
    void deberiaDepurarHechosYGuardarlos() {
        Hecho hechoOriginal = new Hecho("Titulo1", "Desc1", null, null, LocalDate.now(), null);
        Hecho hechoNormalizado = new Hecho("TituloNormal", "Desc", null, null, LocalDate.now(), null);
        when(loader.obtenerHechos())
                .thenReturn(List.of(hechoOriginal));
        when(normalizador.normalizar(List.of(hechoOriginal)))
                .thenReturn(List.of(hechoNormalizado));
        List<Hecho> resultado = actualizador.depurarHechos();
        verify(repositoryHechos).saveAll(List.of(hechoNormalizado));
        assertEquals(1, resultado.size());
        assertEquals("TituloNormal", resultado.get(0).getTitulo());
    }
    @Test
    void deberiaProcesarSolicitudesDeEliminacionAntesDeActualizarColecciones() {

        // ARRANGE
        when(repositoryColecciones.findAll()).thenReturn(List.of());
        when(actualizador.depurarHechos()).thenReturn(List.of());

        // ACT
        actualizador.actualizarColecciones();

        // ASSERT
        verify(gestorSolicitudes, times(1))
                .procesarTodasLasSolicitudes();

        verify(repositoryColecciones).findAll();
    }
}