package utn.ddsi.agregador;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import utn.ddsi.agregador.domain.fuentes.LoaderEstatico;
import utn.ddsi.agregador.domain.fuentes.LoaderProxy;
import utn.ddsi.agregador.domain.hecho.Hecho;


import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class AgregadorApplication {

    public static void main(String[] args) {
        ApplicationContext ctx =SpringApplication.run(AgregadorApplication.class, args);
        System.out.println("Funciona");

        //LoaderEstatico loader = ctx.getBean(LoaderEstatico.class);
        //LoaderProxy metamapa = ctx.getBean(LoaderProxy.class);
        //List<Hecho> res = metamapa.obtenerHechos();
        //System.out.println(res.toString());


    }
}