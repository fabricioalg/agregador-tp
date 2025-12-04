package utn.ddsi.agregador.domain.agregador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.ddsi.agregador.domain.coleccion.*;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.dto.HechoFuenteDTO;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsensoTests {

    private Fuente fuente1;
    private Fuente fuente2;
    private Fuente fuente3;

    private HechoFuenteDTO hecho1;
    private HechoFuenteDTO hecho2;
    private HechoFuenteDTO hecho3;
    private HechoFuenteDTO hechoConflicto;

    private List<Fuente> todasFuentes;
    private List<HechoFuenteDTO> todosHechos;

    @BeforeEach
    void setUp() {
        fuente1 = new Fuente("f1","Fuente1", EnumTipoFuente.ESTATICA);
        fuente2 = new Fuente("f2","Fuente2", EnumTipoFuente.DINAMICA);
        fuente3 = new Fuente("f3","Fuente3", EnumTipoFuente.DINAMICA);

        todasFuentes = Arrays.asList(fuente1, fuente2, fuente3);

        hecho1 = new HechoFuenteDTO(1L, "Hecho A", "Descripcion A", "Fuente1");
        hecho2 = new HechoFuenteDTO(1L, "Hecho A", "Descripcion A", "Fuente2");
        hecho3 = new HechoFuenteDTO(1L, "Hecho A", "Descripcion A", "Fuente3");

        hechoConflicto = new HechoFuenteDTO(1L, "Hecho A", "Descripcion diferente", "Fuente3");

        todosHechos = Arrays.asList(hecho1, hecho2, hecho3);
    }

    // Subclase de prueba que permite controlar las fuentes coincidentes
    abstract class AlgoritmoDeConsensoMock extends AlgoritmoDeConsenso {
        protected Set<String> fuentesMock;

        public void setFuentesMock(Set<String> fuentesMock) {
            this.fuentesMock = fuentesMock;
        }

        @Override
        public Set<String> obtenerFuentesCoincidentes(
                HechoFuenteDTO dataFuenteEvaluada,
                List<HechoFuenteDTO> todosLosDatosDeFuentes,
                List<Fuente> fuentesColeccion) {
            return fuentesMock;
        }
    }

    @Test
    void testConsensoAbsoluto_true() {
        ConsensoAbsoluto algoritmo = new ConsensoAbsoluto() {
            @Override
            public Set<String> obtenerFuentesCoincidentes(HechoFuenteDTO dto, List<HechoFuenteDTO> todos, List<Fuente> fuentes) {
                return new HashSet<>(Arrays.asList("Fuente1", "Fuente2", "Fuente3"));
            }
        };

        assertTrue(algoritmo.aplicar(todasFuentes, hecho1, todosHechos));
    }

    @Test
    void testConsensoAbsoluto_false() {
        ConsensoAbsoluto algoritmo = new ConsensoAbsoluto() {
            @Override
            public Set<String> obtenerFuentesCoincidentes(HechoFuenteDTO dto, List<HechoFuenteDTO> todos, List<Fuente> fuentes) {
                return new HashSet<>(Arrays.asList("Fuente1", "Fuente2")); // Falta Fuente3
            }
        };

        assertFalse(algoritmo.aplicar(todasFuentes, hecho1, todosHechos));
    }

    @Test
    void testMayoriaSimple_true() {
        MayoriaSimple algoritmo = new MayoriaSimple() {
            @Override
            public Set<String> obtenerFuentesCoincidentes(HechoFuenteDTO dto, List<HechoFuenteDTO> todos, List<Fuente> fuentes) {
                return new HashSet<>(Arrays.asList("Fuente1", "Fuente2")); // 2 de 3
            }
        };

        assertTrue(algoritmo.aplicar(todasFuentes, hecho1, todosHechos));
    }

    @Test
    void testMayoriaSimple_false() {
        MayoriaSimple algoritmo = new MayoriaSimple() {
            @Override
            public Set<String> obtenerFuentesCoincidentes(HechoFuenteDTO dto, List<HechoFuenteDTO> todos, List<Fuente> fuentes) {
                return new HashSet<>(Collections.singletonList("Fuente1")); // Solo 1
            }
        };

        assertFalse(algoritmo.aplicar(todasFuentes, hecho1, todosHechos));
    }

    @Test
    void testMencionesMultiples_true() {
        MencionesMultiples algoritmo = new MencionesMultiples() {
            @Override
            public Set<String> obtenerFuentesCoincidentes(HechoFuenteDTO dto, List<HechoFuenteDTO> todos, List<Fuente> fuentes) {
                return new HashSet<>(Arrays.asList("Fuente1", "Fuente2"));
            }
        };

        assertTrue(algoritmo.aplicar(todasFuentes, hecho1, todosHechos));
    }

    @Test
    void testMencionesMultiples_false_por_conflicto() {
        MencionesMultiples algoritmo = new MencionesMultiples() {
            @Override
            public Set<String> obtenerFuentesCoincidentes(HechoFuenteDTO dto, List<HechoFuenteDTO> todos, List<Fuente> fuentes) {
                return new HashSet<>(Arrays.asList("Fuente1", "Fuente2", "Fuente3"));
            }
        };

        List<HechoFuenteDTO> hechosConConflicto = Arrays.asList(hecho1, hecho2, hechoConflicto);
        assertFalse(algoritmo.aplicar(todasFuentes, hecho1, hechosConConflicto));
    }

    @Test
    void testConsensoDefault() {
        ConsensoDefault algoritmo = new ConsensoDefault();
        assertTrue(algoritmo.aplicar(todasFuentes, hecho1, todosHechos));
    }
}