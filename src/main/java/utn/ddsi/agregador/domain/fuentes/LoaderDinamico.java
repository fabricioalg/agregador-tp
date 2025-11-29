package utn.ddsi.agregador.domain.fuentes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import utn.ddsi.agregador.adapter.HechoAdapter;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteDinamicaDTO;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

            List<Hecho> hechosTransformados = new ArrayList<>();
            for (int i = 0; i<hechosDTO.length; i++ ) {
                //String ruta = hechosDTO[i].getFuente().getRuta();
                List<Hecho> transformado =  this.getAdapter().adaptarHechosDeFuenteDinamica(List.of(hechosDTO[i]));
                hechosTransformados.addAll(transformado);
            }

            return hechosTransformados;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener hechos desde " + getRuta(), e);
        }
    }
}
