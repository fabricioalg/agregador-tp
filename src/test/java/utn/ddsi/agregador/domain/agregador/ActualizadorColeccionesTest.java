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
import utn.ddsi.agregador.repository.IRepositoryFuentes;
import utn.ddsi.agregador.repository.IRepositoryHechoXColeccion;
import utn.ddsi.agregador.repository.IRepositoryHechos;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    @Mock private IRepositoryHechoXColeccion repositoryHechoXColeccion;
    @Mock private IRepositoryFuentes repositoryFuentes;

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
            filtrador,
            repositoryHechoXColeccion, repositoryFuentes
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
    void depurarHechos_hechosVacios() {
        Hecho h1 = new Hecho();
        Hecho h2 = new Hecho();
        List<Hecho> hechos = List.of(h1, h2);

        when(loader1.obtenerHechos()).thenReturn(hechos);
        when(loader2.obtenerHechos()).thenReturn(List.of());

        when(normalizador.normalizar(anyList())).thenReturn(hechos);
        List<Hecho> resultado = actualizador.depurarHechos();

        assertEquals(2, resultado.size());
        verify(normalizador).normalizar(anyList());
        verify(repositoryHechos).saveAll(hechos);
    }
    @Test
    void depurarHechos_hechosConDatos() {
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
    void testActualizarColecciones_basico() {
        // --- Mock de hechos ---
        Hecho h1 = new Hecho();
        h1.setId_hecho(1L);
        Hecho h2 = new Hecho();
        h2.setId_hecho(2L);

        when(loader1.obtenerHechos()).thenReturn(List.of(h1));
        when(loader2.obtenerHechos()).thenReturn(Collections.emptyList());
        when(normalizador.normalizar(anyList())).thenReturn(new ArrayList<>(List.of(h1)));

        // --- Mock de colecciones ---
        Coleccion coleccion = mock(Coleccion.class);
        when(coleccion.getId_coleccion()).thenReturn(10L);
        when(repositoryColecciones.findAll()).thenReturn(new ArrayList<>(List.of(coleccion)));

        // --- Mock de hechos totales ---
        when(repositoryHechos.findAll()).thenReturn(new ArrayList<>(List.of(h1, h2)));

        // --- Mock de HechoXColeccion ---
        when(repositoryHechoXColeccion.findByColeccion(10L)).thenReturn(new ArrayList<>());
        when(repositoryHechoXColeccion.findByConjunto(anyLong(), anyLong())).thenReturn(null);

        // --- Mock de condiciones ---
        InterfaceCondicion c1 = mock(InterfaceCondicion.class);
        when(repositoryColecciones.findByIdCondiciones(10L)).thenReturn(new ArrayList<>(List.of(c1)));

        // --- Mock filtrador ---
        when(filtrador.devolverHechosAPartirDe(anyList(), anyList()))
                .thenAnswer(inv -> new ArrayList<>(inv.getArgument(1))); // Devuelve lista mutable

        // --- Mock fuentes ---
        when(repositoryFuentes.findFuentesByColeccion(10L)).thenReturn(new ArrayList<>());

        // --- Ejecutar método ---
        actualizador.actualizarColecciones();

        // --- Verificaciones ---
        verify(repositoryHechoXColeccion, atLeastOnce()).save(any(HechoXColeccion.class));
        verify(gestorSolicitudes).procesarTodasLasSolicitudes();
        verify(repositoryColecciones).saveAll(anyList());
    }
    @Test
    void testActualizarColecciones_conHechosExistentesEnColeccion() {
        Hecho hNuevo = new Hecho("Titulo nuevo", "Desc", null, null,
                LocalDate.now(), null);
        Hecho hExistente = new Hecho("Titulo existente", "Desc", null, null,
                LocalDate.now(), null);

        when(loader1.obtenerHechos()).thenReturn(List.of(hNuevo));
        when(loader2.obtenerHechos()).thenReturn(Collections.emptyList());

        when(normalizador.normalizar(anyList())).thenReturn(List.of(hNuevo));

        when(repositoryHechos.findAll()).thenReturn(List.of(hExistente, hNuevo));

        Coleccion coleccion = mock(Coleccion.class);
        when(coleccion.getId_coleccion()).thenReturn(100L);

        when(repositoryColecciones.findAll()).thenReturn(List.of(coleccion));

        HechoXColeccion hxcExistente = new HechoXColeccion(hExistente, coleccion, false);
        when(repositoryHechoXColeccion.findByColeccion(100L))
                .thenReturn(List.of(hxcExistente));

        InterfaceCondicion c1 = mock(InterfaceCondicion.class);
        when(repositoryColecciones.findByIdCondiciones(100L)).thenReturn(List.of(c1));

        when(filtrador.devolverHechosAPartirDe(anyList(), eq(List.of(hNuevo))))
                .thenReturn(List.of(hNuevo));

        actualizador.actualizarColecciones();

        verify(filtrador, never())
                .devolverHechosAPartirDe(anyList(), eq(List.of(hExistente, hNuevo)));

        verify(filtrador, times(1))
                .devolverHechosAPartirDe(anyList(), eq(List.of(hNuevo)));

        verify(repositoryHechoXColeccion, times(1)).save(any(HechoXColeccion.class));
        verify(gestorSolicitudes).procesarTodasLasSolicitudes();
        verify(repositoryColecciones).saveAll(anyList());
    }
}