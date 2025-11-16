package utn.ddsi.agregador.domain.fuentes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utn.ddsi.agregador.adapter.FuenteEstaticaAdapter;
import utn.ddsi.agregador.adapter.HechoAdapter;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteEstaticaDTO;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoaderEstatico extends Loader {

    private static final Logger logger = LoggerFactory.getLogger(LoaderEstatico.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private String ruta;

    public LoaderEstatico(@Value("${fuente.estatica.url}") String rutaUrl, RestTemplate restTemplate,ObjectMapper objectMapper) throws MalformedURLException {
        super(new URL(rutaUrl));
        this.ruta = rutaUrl;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    public List<Hecho> obtenerHechos() {
        logger.info("Inicio de flujo");
        try{
            ResponseEntity<String> response =
                    restTemplate.exchange(
                            ruta,
                            HttpMethod.GET,
                            null,
                            String.class // <-- Pide el JSON como texto crudo
                    );

            String jsonBody = response.getBody();
            logger.info("Respuesta JSON recibida: {}", jsonBody);

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + ruta, e);
        }
    }

}
