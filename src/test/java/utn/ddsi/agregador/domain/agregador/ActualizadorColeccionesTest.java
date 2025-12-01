package utn.ddsi.agregador.domain.agregador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.domain.condicion.CondicionTitulo;
import utn.ddsi.agregador.domain.condicion.InterfaceCondicion;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import utn.ddsi.agregador.repository.IRepositoryHechos;


import java.time.LocalDate;
import java.util.List;

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
    @Mock private Loader loader1;
    @Mock private Loader loader2;

    @InjectMocks
    private ActualizadorColecciones actualizador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<Loader> loaders = List.of(loader1, loader2);
        actualizador = new ActualizadorColecciones(
                repositoryColecciones,
                repositoryHechos,
                normalizador,
                gestorSolicitudes,
                loaders,
                filtrador

        );
    }

    @Test
    void traerHechosDeLoaders_devuelveHechosDeTodosLosLoaders() {

        when(loader1.obtenerHechos()).thenReturn(List.of(new Hecho()));
        when(loader2.obtenerHechos()).thenReturn(List.of(new Hecho(), new Hecho()));

        List<Hecho> result = actualizador.traerHechosDeLoaders();

        assertEquals(3, result.size());
        verify(loader1, times(1)).obtenerHechos();
        verify(loader2, times(1)).obtenerHechos();
    }
    @Test
    void depurarHechos_normalizaYGuardaHechos() {
        Hecho h1 = new Hecho();
        Hecho h2 = new Hecho();
        List<Hecho> hechos = List.of(h1, h2);

        when(loader1.obtenerHechos()).thenReturn(hechos);
        when(loader2.obtenerHechos()).thenReturn(List.of());
        when(normalizador.normalizar(anyList())).thenReturn(hechos);

        when(normalizador.normalizar(anyList())).thenReturn(hechos);
        List<Hecho> resultado = actualizador.depurarHechos();

        assertEquals(2, resultado.size());
        verify(normalizador).normalizar(anyList());
        verify(repositoryHechos).saveAll(hechos);
    }
    @Test
    void deberiaDepurarHechosYGuardarlos() {
        Hecho hechoOriginal = new Hecho("Titulo1", "Desc1", null, null, LocalDate.now(), null);
        Hecho hechoNormalizado = new Hecho("TituloNormal", "Desc", null, null, LocalDate.now(), null);
        when(loader1.obtenerHechos())
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
        when(repositoryColecciones.findAll()).thenReturn(List.of());
        when(actualizador.depurarHechos()).thenReturn(List.of());

        actualizador.actualizarColecciones();

        verify(gestorSolicitudes, times(1))
                .procesarTodasLasSolicitudes();

        verify(repositoryColecciones).findAll();
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
                List.of(loader1, loader2),
                filtrador
        ));

        doReturn(hechos).when(actualizador).depurarHechos();

        InterfaceCondicion condicionMock = mock(InterfaceCondicion.class);
        lenient().when(condicionMock.cumpleCondicion(any())).thenReturn(true);

        lenient().when(filtrador.devolverHechosAPartirDe(any(), any()))
                .thenReturn(hechos);

        Coleccion coleccion = new Coleccion("titulo");
        coleccion.setCondicionDePertenencia(List.of(condicionMock));
        when(repositoryColecciones.findAll()).thenReturn(List.of(coleccion));

        actualizador.actualizarColecciones();

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
    void actualizarColeccionesConHechosVacios() {
        Hecho h1 = new Hecho();
        Hecho h2 = new Hecho();
        List<Hecho> hechosNuevos = List.of(h1, h2);

        when(loader1.obtenerHechos()).thenReturn(hechosNuevos);
        when(loader2.obtenerHechos()).thenReturn(List.of());
        when(normalizador.normalizar(any())).thenReturn(hechosNuevos);
        when(repositoryHechos.findAll()).thenReturn(hechosNuevos);

        CondicionTitulo cond = mock(CondicionTitulo.class);

        Coleccion c1 = new Coleccion();
        assertNotNull(c1.getHechos());
        c1.agregarCriterioDePertenencia(cond);

        Coleccion c2 = new Coleccion();
        c2.agregarCriterioDePertenencia(cond);

        List<Coleccion> colecciones = List.of(c1, c2);
        when(repositoryColecciones.findAll()).thenReturn(colecciones);
        when(filtrador.devolverHechosAPartirDe(any(), any())).thenReturn(hechosNuevos);

        actualizador.actualizarColecciones();

        verify(gestorSolicitudes, times(1)).procesarTodasLasSolicitudes();
        verify(repositoryColecciones, times(1)).saveAll(colecciones);
        assertEquals(2, c1.getHechos().size());
        assertEquals(2, c2.getHechos().size());
    }
}