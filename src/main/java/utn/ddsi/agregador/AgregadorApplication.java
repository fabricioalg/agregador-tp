package utn.ddsi.agregador;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import utn.ddsi.agregador.domain.agregador.ActualizadorColecciones;
import utn.ddsi.agregador.domain.fuentes.LoaderEstatico;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.util.List;

@SpringBootApplication
public class AgregadorApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(AgregadorApplication.class, args);

        LoaderEstatico loader = context.getBean(LoaderEstatico.class);
        List<Hecho> res = loader.obtenerHechos();
        System.out.println(res.toString());

        System.out.println("Funciona");

    }
/*
    @Bean
    public CommandLineRunner test(ActualizadorColecciones actualizador) {
        return args -> {
            System.out.println("Actualizador cargado: " + actualizador);
            List<Hecho> hechos = actualizador.depurarHechos();
            System.out.println("Hechos normalizados: " + hechos.size());
        };
    }*/
}