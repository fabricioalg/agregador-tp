package utn.ddsi.agregador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import utn.ddsi.agregador.domain.agregador.ActualizadorColecciones;
import utn.ddsi.agregador.domain.agregador.FiltradorDeHechos;
import utn.ddsi.agregador.domain.agregador.Normalizador;
import utn.ddsi.agregador.domain.fuentes.Loader;
import utn.ddsi.agregador.domain.fuentes.LoaderEstatico;
import utn.ddsi.agregador.domain.solicitudEliminacion.DetectorBasicoDeSpam;
import utn.ddsi.agregador.domain.solicitudEliminacion.GestorDeSolicitudes;
import utn.ddsi.agregador.repository.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AgregadorApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AgregadorApplication.class, args);
        System.out.println("Funciona");

        IRepositoryProvincias repoProv = ctx.getBean(IRepositoryProvincias.class);
        IRepositoryCategorias repoCat = ctx.getBean(IRepositoryCategorias.class);
        IRepositoryHechos repoHecho = ctx.getBean(IRepositoryHechos.class);
        IRepositoryColecciones repoCol = ctx.getBean(IRepositoryColecciones.class);
        IRepositorySolicitudes repoSol = ctx.getBean(IRepositorySolicitudes.class);
        IRepositoryHechoXColeccion repoHxC = ctx.getBean(IRepositoryHechoXColeccion.class);


        LoaderEstatico loaderEs = ctx.getBean(LoaderEstatico.class);
        FiltradorDeHechos filter = new FiltradorDeHechos();
        Normalizador normalizador = new Normalizador(repoCat, repoProv);
        DetectorBasicoDeSpam detectorBasico = new DetectorBasicoDeSpam();
        List<Loader> loaders = new ArrayList<>();
        loaders.add(loaderEs);
        GestorDeSolicitudes gestBasico = new GestorDeSolicitudes(repoSol, detectorBasico);


        ActualizadorColecciones act = new ActualizadorColecciones(repoCol, repoHecho, normalizador, gestBasico, loaders, filter, repoHxC);
        act.actualizarColecciones();

        //act.depurarHechos();
        /*
        ActualizadorColecciones act = ctx.getBean(ActualizadorColecciones.class);
        act.depurarHechos();
        act.ejecutarAlgoritmosDeConsenso();
        */
    }
}
