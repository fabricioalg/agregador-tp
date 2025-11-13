package utn.ddsi.agregador.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.time.LocalDate;
import java.util.List;

// ESTO ES PARA EL TEST DE INTEGRACION Y PUEDE SER BORRADO
/*
@Component
@Profile("test")
public class FakeLoader extends Loader {

    public FakeLoader() {
        super(null); // el constructor del padre no se usa en test
    }

    @Override
    public List<Hecho> obtenerHechos(LocalDate fechaLimite) {
        Hecho h = new Hecho("Test Titulo", "Test Desc", null, null, fechaLimite.minusDays(1), null);
        return List.of(h);
    }
}*/