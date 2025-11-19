package utn.ddsi.agregador.domain.agregador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.IRepositoryColecciones;
import utn.ddsi.agregador.repository.IRepositoryHechos;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActualizadorColeccionesSolicitudTest {

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
        actualizador = new ActualizadorColecciones(
                repositoryColecciones,
                repositoryHechos,
                normalizador,
                gestorSolicitudes,
                List.of(loader),
                filtrador
        );
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
// to do hacer tests con el gestor de solicitudes
}