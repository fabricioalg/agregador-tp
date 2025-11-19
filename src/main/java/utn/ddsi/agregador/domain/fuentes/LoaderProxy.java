package utn.ddsi.agregador.domain.fuentes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utn.ddsi.agregador.adapter.HechoAdapter;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteEstaticaDTO;
import utn.ddsi.agregador.dto.HechoFuenteProxyDTO;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoaderProxy extends Loader {
    private static final Logger logger = LoggerFactory.getLogger(LoaderProxy.class);
    private final RestTemplate restTemplate;
    private String ruta;
    private HechoAdapter adapter;

    public LoaderProxy(@Value("${fuente.metamapa.url}") String rutaUrl, RestTemplate restTemplate, HechoAdapter adapter) throws MalformedURLException {
        super(new URL(rutaUrl));
        this.ruta = rutaUrl;
        this.restTemplate = restTemplate;
        this.adapter = adapter;
    }


    public List<Hecho> obtenerHechos(String ruta) {
        try{
            ResponseEntity<HechoFuenteProxyDTO[]> response =
                    restTemplate.exchange(
                            ruta+"/hechos",
                            HttpMethod.GET,
                            null,
                            HechoFuenteProxyDTO[].class
                    );

            HechoFuenteProxyDTO[] hechosDTO = response.getBody();

            if (hechosDTO == null) {
                return Collections.emptyList();
            }
            return Arrays.stream(hechosDTO)
                    .map(adapter::adaptarDesdeFuenteProxy)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + ruta, e);
        }
    }

}
