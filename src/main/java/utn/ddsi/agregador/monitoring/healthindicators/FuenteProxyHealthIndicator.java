package utn.ddsi.agregador.monitoring.healthindicators;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.Duration;

@Component("fuenteProxy")
public class FuenteProxyHealthIndicator extends AbstractDependencyHealthIndicator {

    private final RestTemplate restTemplate;
    private final String url;

    public FuenteProxyHealthIndicator(
            RestTemplateBuilder builder,
            @Value("${fuente.proxy.url}") String url) {

        this.restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(3))
                .build();
        this.url = url;
    }

    @Override
    protected String dependencyName() {
        return "fuenteProxy";
    }

    @Override
    protected String downMessage() {
        return "Fuente proxy no disponible";
    }

    @Override
    public boolean estaDisponible() {
        try {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(url, String.class);

            return response.getStatusCode().is2xxSuccessful();

        } catch (RestClientException ex) {
            return false;
        }
    }
}
