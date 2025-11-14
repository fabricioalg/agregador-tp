package utn.ddsi.agregador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import utn.ddsi.agregador.domain.agregador.FiltradorDeHechos;
import utn.ddsi.agregador.domain.agregador.Normalizador;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.solicitudEliminacion.DetectorBasicoDeSpam;
import utn.ddsi.agregador.domain.solicitudEliminacion.DetectorDeSpam;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.domain.solicitudEliminacion.SolicitudEliminacion;
import utn.ddsi.agregador.repository.IRepositorySolicitudes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
/*
@Configuration
public class BeansConfig {

    @Bean
    public Normalizador normalizador() {
        return new Normalizador();
    }

    @Bean
    public Loader loader() throws MalformedURLException {
        //url de los loaders
        return new Loader(new URL("https://tu-url-de-railway.com/hechos.json"));
    }

    @Bean
    public FiltradorDeHechos filtradorDeHechos() {
        return new FiltradorDeHechos();
    }

    @Bean
    public List<Loader> loaders(Loader loader) {
        return List.of(loader);
    }

    @Bean
    public DetectorDeSpam detectorDeSpam() {
        return new DetectorBasicoDeSpam();
    }

    @Bean
    public GestorDeSolicitudes gestorDeSolicitudes(
            IRepositorySolicitudes repository
    ) {
        return new GestorDeSolicitudes(repository);
    }
}*/