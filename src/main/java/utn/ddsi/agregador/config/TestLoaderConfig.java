package utn.ddsi.agregador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import utn.ddsi.agregador.domain.fuentes.Loader;

import java.util.List;
/*
//ESTO ES PARA EL TEST DE INTEGRACION Y PUEDE SER BORRADO
@Configuration
@Profile("test")
public class TestLoaderConfig {

    @Bean
    public List<Loader> loaders(FakeLoader fakeLoader) {
        return List.of(fakeLoader);
    }
}
*/