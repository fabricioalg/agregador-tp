package utn.ddsi.agregador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"utn.ddsi.agregador.domain"})
@EnableJpaRepositories(basePackages = {"utn.ddsi.agregador.repository"})
public class AgregadorApplication {

	public static void main(String[] args) {
        SpringApplication.run(AgregadorApplication.class, args);
        System.out.println("Funciona");
    }

}
