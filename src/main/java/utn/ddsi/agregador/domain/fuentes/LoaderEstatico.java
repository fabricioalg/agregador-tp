package utn.ddsi.agregador.domain.fuentes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import utn.ddsi.agregador.adapter.HechoAdapter;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.dto.HechoFuenteEstaticaDTO;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class LoaderEstatico extends Loader {
    private HechoAdapter adapter;
    private URL url;
    public LoaderEstatico(URL url) {
        super(url);
        this.adapter = new HechoAdapter();
    }
    public List<Hecho> obtenerHechos(String ruta) {
        try{
            URL endpoint = new URL(url+"/"+ruta);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                List<HechoFuenteEstaticaDTO> hechosDTO = mapper.readValue(
                        connection.getInputStream(),
                        new TypeReference<List<HechoFuenteEstaticaDTO>>() {
                        });

                List<Hecho> hechos = hechosDTO.stream()
                        .map(dto -> adapter.adaptarDesdeFuenteEstatica(dto))
                        .collect(Collectors.toList());
                return hechos.stream().toList();
            }else {
                throw new RuntimeException("Error al obtener hechos: HTTP " + connection.getResponseCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la fuente estática: " + url);
        }
    }
}
