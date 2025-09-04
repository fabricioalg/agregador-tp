package utn.ddsi.agregador.domain;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class LoaderDinamico extends Loader{
    public LoaderDinamico(URL url) {
        super(url);
    }
    @Override
    public List<Hecho> obtenerHechos(LocalDate fechaLimite) {
        return super.obtenerHechos(fechaLimite);
    }
}
