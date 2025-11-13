package utn.ddsi.agregador.domain.agregador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.coleccion.ConsensoDefault;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Hecho hechoOriginal = new Hecho("Titulo1", "Desc1", null, null, LocalDate.now(), null);
        Hecho hechoNormalizado = new Hecho("TituloNormal", "Desc", null, null, LocalDate.now(), null);
        Coleccion coleccion = new Coleccion("titulo", "descripcion", null);

        when(loader.obtenerHechos(any(LocalDate.class)))
                .thenReturn(Collections.singletonList(hechoOriginal));
        when(normalizador.normalizar(anyList()))
                .thenReturn(Collections.singletonList(hechoNormalizado));
        when(repositoryColecciones.findAll())
                .thenReturn(Collections.singletonList(coleccion));
        when(filtrador.devolverHechosAPartirDe(any(), anyList()))
                .thenReturn(Collections.singletonList(hechoNormalizado));
        actualizador.actualizarColecciones();
        ArgumentCaptor<List<Hecho>> captor = ArgumentCaptor.forClass(List.class);
        verify(repositoryHechos).saveAll(captor.capture());
        List<Hecho> listaGuardada = captor.getValue();
        assertTrue(listaGuardada.contains(hechoNormalizado));
    }
    @Test
    void deberiaDepurarHechosYGuardarlos() {
        Hecho hechoOriginal = new Hecho("Titulo1", "Desc1", null, null, LocalDate.now(), null);
        Hecho hechoNormalizado = new Hecho("TituloNormal", "Desc", null, null, LocalDate.now(), null);
        when(loader.obtenerHechos(any(LocalDate.class)))
                .thenReturn(List.of(hechoOriginal));
        when(normalizador.normalizar(List.of(hechoOriginal)))
                .thenReturn(List.of(hechoNormalizado));
        List<Hecho> resultado = actualizador.depurarHechos();
        verify(repositoryHechos).saveAll(List.of(hechoNormalizado));
        assertEquals(1, resultado.size());
        assertEquals("TituloNormal", resultado.get(0).getTitulo());
    }
}