package utn.ddsi.agregador.domain.agregador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utn.ddsi.agregador.domain.coleccion.Coleccion;
import utn.ddsi.agregador.domain.coleccion.HechoXColeccion;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import utn.ddsi.agregador.repository.IRepositoryHechoXColeccion;
import utn.ddsi.agregador.repository.IRepositoryHechos;
import utn.ddsi.agregador.utils.EnumTipoDeAlgoritmo;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AplicarConsensoTest {

    @Mock
    private IRepositoryColecciones repoColecciones;
    @Mock
    private IRepositoryHechoXColeccion repoHXC;
    @Mock
    private IRepositoryHechos repoHechos;
    @Mock
    private FiltradorDeHechos filtrador;
    @Mock
    private Normalizador normalizador;
    @Mock
    private GestorDeSolicitudes gestor;

    @InjectMocks
    private ActualizadorColecciones service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------------------------------------------------
    // Método auxiliar para crear un HXC de prueba
    // -------------------------------------------------------------
    private HechoXColeccion crearHXC(Hecho hecho, Coleccion col) {
        return new HechoXColeccion(hecho, col, false);
    }

    // -------------------------------------------------------------
    // Test Genérico para TODOS los algoritmos
    // -------------------------------------------------------------
    private void testearAlgoritmo(EnumTipoDeAlgoritmo tipoAlgoritmo,
                                  boolean esperadoConsenso) {

        // -------- Colección ----------
        Coleccion col = new Coleccion("Test");
        col.setTipoDeAlgoritmo(tipoAlgoritmo);

        // -------- Hecho y HXC ---------
        Hecho h = new Hecho();
        h.setFuente(new Fuente(1,"F1","fuente", EnumTipoFuente.ESTATICA));

        HechoXColeccion hxc = crearHXC(h, col);

        // -------- Stubs -----------
        when(repoColecciones.findAll()).thenReturn(List.of(col));
        when(repoHXC.findByColeccion(col.getId_coleccion()))
                .thenReturn(List.of(hxc));

        // -------- Ejecutar ----------
        service.ejecutarAlgoritmosDeConsenso();

        // -------- Verificación ----------
        ArgumentCaptor<HechoXColeccion> captor = ArgumentCaptor.forClass(HechoXColeccion.class);
        verify(repoHXC, times(1)).save(captor.capture());

        HechoXColeccion guardado = captor.getValue();

        assertEquals(esperadoConsenso,
                guardado.getConsensuado(),
                "El algoritmo " + tipoAlgoritmo + " no produjo el valor esperado");
    }

    // =============================================================
    //                    TESTS ESPECÍFICOS
    // =============================================================

    @Test
    void testConsensoDefault() {
        testearAlgoritmo(EnumTipoDeAlgoritmo.DEFAULT, true);
    }

    @Test
    void testConsensoAbsoluto() {
        // Para absoluto, una sola fuente no coincide con todas -> false
        testearAlgoritmo(EnumTipoDeAlgoritmo.ABSOLUTA, false);
    }

    @Test
    void testMayoriaSimple() {
        // MayoriaSimple -> con 1 fuente total, mayoría mínima = 1 -> TRUE
        testearAlgoritmo(EnumTipoDeAlgoritmo.MAYORIA_SIMPLE, true);
    }

    @Test
    void testMencionesMultiples() {
        // Necesita 2 fuentes coincidentes -> con solo 1 => FALSE
        testearAlgoritmo(EnumTipoDeAlgoritmo.MULTIPLES_MENCIONES, false);
    }
}