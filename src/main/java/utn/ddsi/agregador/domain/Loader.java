package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Loader {
    private URL url;

    public Loader(URL url) {
        this.url = url;
    }

    public List<Hecho> obtenerHechos(LocalDate fechaLimite) {
        try (InputStream in = url.openStream()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // para LocalDate

            List<Hecho> hechos = mapper.readValue(in, new TypeReference<List<Hecho>>() {});

            return hechos.stream()
                    .filter(h -> !h.getFechaDeCarga().isAfter(fechaLimite))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + url, e);
        }
    }
}
