package utn.ddsi.agregador.domain.agregador;

import org.junit.jupiter.api.Test;
import utn.ddsi.agregador.domain.coleccion.*;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.utils.EnumTipoDeAlgoritmo;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsensoTests {
    @Test
    void constructorTitulo_inicializaAlgoritmoDefault() {
        Coleccion col = new Coleccion("test");

        assertEquals(EnumTipoDeAlgoritmo.DEFAULT, col.getTipoDeAlgoritmo());
        assertTrue(col.getAlgoritmoDeConsenso() instanceof ConsensoDefault);
    }
    @Test
    void defaultSiempreConsensua() {
        ConsensoDefault algoritmo = new ConsensoDefault();

        Hecho h = new Hecho();
        HechoXColeccion hxc = new HechoXColeccion(h, null, false);

        boolean result = algoritmo.aplicar(hxc, List.of(hxc), List.of());

        assertTrue(result);
    }
    @Test
    void mayoriaSimple_conInstanciasDistintasPeroMismoContenido_devuelveTrue() {
        Fuente f1 = new Fuente();
        Fuente f2 = new Fuente();
        Fuente f3 = new Fuente();

        Hecho h1 = new Hecho("evento","desc", null, null, LocalDate.now(), f1);
        Hecho h2 = new Hecho("evento","desc", null, null, LocalDate.now(), f2);

        HechoXColeccion hx1 = new HechoXColeccion(h1, null, false);
        HechoXColeccion hx2 = new HechoXColeccion(h2, null, false);

        MayoriaSimple alg = new MayoriaSimple();

        boolean res = alg.aplicar(hx1, List.of(hx1, hx2), List.of(f1,f2,f3));
        assertTrue(res);
    }

    @Test
    void noEsConsensuado_siHayConflictoDeMismoTitulo() {
        Fuente f1 = new Fuente(1,"F1","url", EnumTipoFuente.DINAMICA);
        Fuente f2 = new Fuente(2,"F2","url", EnumTipoFuente.DINAMICA);
        Fuente f3 = new Fuente(3,"F3","url", EnumTipoFuente.DINAMICA);

        Hecho hEval = new Hecho("Incendio", "Fuerte incendio en el centro", null, null, LocalDate.now(), f1);

        // Mismo título pero atributos distintos → genera conflicto
        Hecho hConflicto = new Hecho("Incendio", "Otro incendio distinto", null, null, LocalDate.now(), f2);

        // Otro hecho más para completar las fuentes
        Hecho hOtro = new Hecho("Corte de luz", "En toda la ciudad", null, null, LocalDate.now(), f3);

        // Wrappers
        HechoXColeccion hxEval = new HechoXColeccion(hEval, null, false);
        HechoXColeccion hxConf = new HechoXColeccion(hConflicto, null, false);
        HechoXColeccion hxOtro = new HechoXColeccion(hOtro, null, false);

        List<HechoXColeccion> hechos = List.of(hxEval, hxConf, hxOtro);
        List<Fuente> fuentes = List.of(f1, f2, f3);

        MencionesMultiples algoritmo = new MencionesMultiples();

        boolean resultado = algoritmo.aplicar(hxEval, hechos, fuentes);

        assertFalse(resultado); // ✔ Debe detectar conflicto
    }
    @Test
    void consensuado_siMasDeLaMitadDeLasFuentesLoTienen() {

        Fuente f1 = new Fuente(1,"F1","url", EnumTipoFuente.DINAMICA);
        Fuente f2 = new Fuente(2,"F2","url", EnumTipoFuente.DINAMICA);
        Fuente f3 = new Fuente(3,"F3","url", EnumTipoFuente.DINAMICA);

        // Hechos iguales (mismos atributos)
        Hecho h1 = new Hecho("Accidente", "Choque múltiple", null, null, LocalDate.now(), f1);
        Hecho h2 = new Hecho("Accidente", "Choque múltiple", null, null, LocalDate.now(), f2);

        // Un hecho distinto
        Hecho hOtro = new Hecho("Evento", "Nada que ver", null, null, LocalDate.now(), f3);

        HechoXColeccion hx1 = new HechoXColeccion(h1, null, false);
        HechoXColeccion hx2 = new HechoXColeccion(h2, null, false);
        HechoXColeccion hxOtro = new HechoXColeccion(hOtro, null, false);

        List<HechoXColeccion> hechos = List.of(hx1, hx2, hxOtro);
        List<Fuente> fuentes = List.of(f1, f2, f3); // tamaño 3 → mayoría = 2

        MayoriaSimple algoritmo = new MayoriaSimple();

        boolean res = algoritmo.aplicar(hx1, hechos, fuentes);

        assertTrue(res); // 2 fuentes coinciden
    }

    @Test
    void noConsensuado_siNoSuperaLaMitad() {
        Fuente f1 = new Fuente();
        Fuente f2 = new Fuente();
        Fuente f3 = new Fuente();

        Hecho h1 = new Hecho();
        h1.setTitulo("evento");
        h1.setFuente(f1);

        HechoXColeccion hx1 = new HechoXColeccion(h1, null, false);

        MayoriaSimple alg = new MayoriaSimple();

        boolean result = alg.aplicar(
                hx1,
                List.of(hx1),
                List.of(f1, f2, f3)
        );

        assertFalse(result);
    }
    @Test
    void consensuado_siTodasLasFuentesTienenElHecho() {
        Fuente f1 = new Fuente();
        Fuente f2 = new Fuente();

        Hecho h1 = new Hecho();
        h1.setTitulo("evento");
        h1.setFuente(f1);

        Hecho h2 = new Hecho();
        h2.setTitulo("evento");
        h2.setFuente(f2);

        HechoXColeccion hx1 = new HechoXColeccion(h1, null, false);
        HechoXColeccion hx2 = new HechoXColeccion(h2, null, false);

        ConsensoAbsoluto alg = new ConsensoAbsoluto();

        boolean result = alg.aplicar(
                hx1,
                List.of(hx1, hx2),
                List.of(f1, f2)
        );

        assertTrue(result);
    }

    @Test
    void noConsensuado_siAlgunFuenteNoLoTiene() {
        Fuente f1 = new Fuente();
        Fuente f2 = new Fuente();

        Hecho h1 = new Hecho();
        h1.setTitulo("evento");
        h1.setFuente(f1);

        HechoXColeccion hx1 = new HechoXColeccion(h1, null, false);

        ConsensoAbsoluto alg = new ConsensoAbsoluto();

        boolean result = alg.aplicar(
                hx1,
                List.of(hx1),
                List.of(f1, f2) // f2 no lo tiene
        );

        assertFalse(result);
    }

}