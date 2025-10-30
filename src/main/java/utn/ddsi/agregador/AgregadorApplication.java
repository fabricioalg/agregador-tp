package utn.ddsi.agregador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utn.ddsi.agregador.utils.BDUtils;

import javax.persistence.EntityManager;

@SpringBootApplication
public class AgregadorApplication {

	public static void main(String[] args) {
        SpringApplication.run(AgregadorApplication.class, args);
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
	}

}
