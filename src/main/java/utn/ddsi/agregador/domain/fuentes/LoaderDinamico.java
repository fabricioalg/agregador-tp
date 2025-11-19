package utn.ddsi.agregador.domain.fuentes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import utn.ddsi.agregador.adapter.HechoAdapter;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDinamicaDTO;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LoaderDinamico extends Loader {
    private URL url;
    private HechoAdapter adapter;

    public LoaderDinamico(URL url, HechoAdapter adapter) {
        super(url);
        this.adapter = adapter;
    }

    @Override
    public List<Hecho> obtenerHechos(LocalDate fechaLimite) {
        try {
            // Hacer una petición GET al endpoint de exportación de la fuente dinámica
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                // Leer los DTOs de la fuente dinámica
                List<HechoFuenteDinamicaDTO> hechosDTO = mapper.readValue(
                    connection.getInputStream(),
                    new TypeReference<List<HechoFuenteDinamicaDTO>>() {}
                );

                // Adaptar los hechos de la fuente dinámica al modelo del agregador
                List<Hecho> hechos = hechosDTO.stream()
                    .map(dto -> adapter.adaptarDesdeFuenteDinamica(dto))
                    .collect(Collectors.toList());

                // Filtrar por fecha límite
                return hechos.stream()
                    .filter(h -> !h.getFechaDeCarga().isAfter(fechaLimite))
                    .collect(Collectors.toList());
            } else {
                throw new RuntimeException("Error al obtener hechos: HTTP " + connection.getResponseCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + url, e);
        }
    }
}
