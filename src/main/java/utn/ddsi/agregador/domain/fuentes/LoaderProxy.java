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

    private final RestTemplate restTemplate;  //Por ahí se puede pasar a Loader

    //ESta bien que este harcodeado porque debería solo apuntar a una LoaderProxy
    public LoaderProxy(@Value("${loader.proxy.url}") String rutaUrl, RestTemplate restTemplate, HechoAdapter adapter) throws MalformedURLException {
        setRuta(rutaUrl);
        this.restTemplate = restTemplate;
        setAdapter(adapter);
    }

    @Override
    public List<Hecho> obtenerHechos() {
        try{
            ResponseEntity<HechoFuenteProxyDTO[]> response =
                    restTemplate.exchange(
                            getRuta()+"/hechos",
                            HttpMethod.GET,
                            null,
                            HechoFuenteProxyDTO[].class
                    );

            HechoFuenteProxyDTO[] hechosDTO = response.getBody();

            if (hechosDTO == null) {
                return Collections.emptyList();
            }
            return Arrays.stream(hechosDTO)
                    .map(getAdapter()::adaptarDesdeFuenteProxy)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + getRuta(), e);
        }
    }

}
