package utn.ddsi.agregador.domain.fuentes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import utn.ddsi.agregador.adapter.HechoAdapter;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDinamicaDTO;
import utn.ddsi.agregador.dto.HechoFuenteProxyDTO;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LoaderDinamico extends Loader {
private final RestTemplate restTemplate;


    public LoaderDinamico(@Value("${fuente.dinamico.url}") String rutaUrl, RestTemplate restTemplate, HechoAdapter adapter) throws MalformedURLException {
        this.setRuta(rutaUrl);
        this.restTemplate = restTemplate;
        this.setAdapter(adapter);
    }

    //Ver lo de la hora donde agregar
    @Override
    public List<Hecho> obtenerHechos() {
        try{
            ResponseEntity<HechoFuenteDinamicaDTO[]> response =
                    restTemplate.exchange(
                            getRuta()+"/hechos",
                            HttpMethod.GET,
                            null,
                            HechoFuenteDinamicaDTO[].class
                    );

            HechoFuenteDinamicaDTO[] hechosDTO = response.getBody();

            if (hechosDTO == null) {
                return Collections.emptyList();
            }
            return Arrays.stream(hechosDTO)
                    .map(getAdapter()::adaptarDesdeFuenteDinamica)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + getRuta(), e);
        }
    }
}
