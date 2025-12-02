package utn.ddsi.agregador.domain.fuentes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utn.ddsi.agregador.adapter.HechoAdapter;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDinamicaDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class LoaderDinamico extends Loader {
    private final RestTemplate restTemplate;

    public LoaderDinamico(@Value("${fuente.dinamica.url}") String rutaUrl, RestTemplate restTemplate, HechoAdapter adapter) {
        this.setRuta(rutaUrl);
        this.restTemplate = restTemplate;
        this.setAdapter(adapter);
    }

    @Override
    public List<Hecho> obtenerHechos() {
        try {
            ResponseEntity<HechoFuenteDinamicaDTO[]> response =
                    restTemplate.exchange(
                            getRuta() + "/hechos",
                            HttpMethod.GET,
                            null,
                            HechoFuenteDinamicaDTO[].class
                    );

            HechoFuenteDinamicaDTO[] hechosDTO = response.getBody();

            if (hechosDTO == null || hechosDTO.length == 0) {
                return Collections.emptyList();
            }

            return this.getAdapter().adaptarHechosDeFuenteDinamica(Arrays.asList(hechosDTO));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + getRuta(), e);
        }
    }
}
