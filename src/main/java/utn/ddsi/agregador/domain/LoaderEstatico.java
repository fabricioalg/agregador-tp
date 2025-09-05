package utn.ddsi.agregador.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;
import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class LoaderEstatico extends Loader {
    public LoaderEstatico(URL url) {
        super(url);
    }
    @Override
    public List<Hecho> obtenerHechos(LocalDate fechaLimite) {return super.obtenerHechos(fechaLimite);}

}
