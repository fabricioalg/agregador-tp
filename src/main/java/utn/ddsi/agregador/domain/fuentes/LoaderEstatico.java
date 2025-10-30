package utn.ddsi.agregador.domain.fuentes;

import utn.ddsi.agregador.domain.hecho.Hecho;

import java.time.LocalDate;
import java.net.URL;
import java.util.List;

public class LoaderEstatico extends Loader {
    public LoaderEstatico(URL url) {
        super(url);
    }
    @Override
    public List<Hecho> obtenerHechos(LocalDate fechaLimite) {return super.obtenerHechos(fechaLimite);}

}
