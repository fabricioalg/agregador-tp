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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoaderEstatico extends Loader {

    private final RestTemplate restTemplate;


    public LoaderEstatico(@Value("${fuente.estatica.url}") String rutaUrl, RestTemplate restTemplate, HechoAdapter adapter) throws MalformedURLException {
        this.setRuta(rutaUrl);
        this.restTemplate = restTemplate;
        this.setAdapter(adapter);
    }


    public List<Hecho> obtenerHechos() {
        try{
            ResponseEntity<HechoFuenteEstaticaDTO[][]> response =
                    restTemplate.exchange(
                            this.getRuta() + "/hechos",
                            HttpMethod.GET,
                            null,
                            HechoFuenteEstaticaDTO[][].class
                    );

            HechoFuenteEstaticaDTO[][] hechosDTO = response.getBody();

            if (hechosDTO == null) {
                return Collections.emptyList();
            }

            List<Hecho> hechosTransformados = new ArrayList<>();
            for (int i = 0; i<hechosDTO.length; i++ ) {
                String ruta = hechosDTO[i][0].getFuente().getRuta();
                List<Hecho> transformado =  this.getAdapter().adaptarHechosDeFuenteEstatica(ruta, List.of(hechosDTO[i]));
                hechosTransformados.addAll(transformado);
            }

            return hechosTransformados;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + this.getRuta(), e);
        }
    }

}
