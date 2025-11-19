package utn.ddsi.agregador.domain.fuentes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import utn.ddsi.agregador.adapter.HechoAdapter;
import utn.ddsi.agregador.domain.hecho.Hecho;

import java.io.InputStream;
import java.time.LocalDate;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public abstract class Loader {
    private String  ruta;
    private HechoAdapter adapter;
    private LocalDate fechaLimite;


    public Loader() {}

    public abstract List<Hecho> obtenerHechos();
    }

