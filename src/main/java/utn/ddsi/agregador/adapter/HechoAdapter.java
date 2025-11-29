package utn.ddsi.agregador.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.*;
import utn.ddsi.agregador.dto.FuenteDTO;
import utn.ddsi.agregador.dto.HechoFuenteDinamicaDTO;
import utn.ddsi.agregador.dto.HechoFuenteEstaticaDTO;
import utn.ddsi.agregador.dto.HechoFuenteProxyDTO;
import utn.ddsi.agregador.repository.IRepositoryCategorias;
import utn.ddsi.agregador.repository.IRepositoryFuentes;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class HechoAdapter {

    @Value("${fuente.proxy.url}")
    private String proxyUrl;
    @Value("${fuente.dinamica.url}")
    private String dinamicaUrl;


    @Autowired
    private final IRepositoryFuentes repoFuente;
    private final IRepositoryCategorias repoCategoria;
    public HechoAdapter(IRepositoryFuentes repoFuente, IRepositoryCategorias repoCategoria) {
        this.repoFuente = repoFuente;
        this.repoCategoria = repoCategoria;
    }

    public Hecho adaptarDesdeFuenteDinamica(HechoFuenteDinamicaDTO dto, Fuente fuente) {
        // convierto componentes
        Categoria categoria = this.repoCategoria.findByNombre(dto.getCategoria());
        if(categoria == null) {
            categoria = new Categoria();
            categoria.setNombre(dto.getCategoria());
            categoria = this.repoCategoria.save(categoria);
        }

        Ubicacion ubicacion = null;
        if (dto.getUbicacion() != null) {
            ubicacion = new Ubicacion(
                dto.getUbicacion().getLatitud(),
                dto.getUbicacion().getLongitud()
            );
        }

        // determino si es multimedia o texto según tenga adjunto
        Hecho hecho= new Hecho(); //cargar de datos
        /*
        if (dto.getAdjunto() != null && dto.getAdjunto().getUrl() != null && !dto.getAdjunto().getUrl().isEmpty()) {
            // Es multimedia
            HechoMultimedia hechoMultimedia = new HechoMultimedia(
                dto.getTitulo(),
                dto.getDescripcion(),
                categoria,
                ubicacion,
                dto.getFecha(),
                fuente
            );
            hechoMultimedia.rutaAlContenido = dto.getAdjunto().getUrl();
            hecho = hechoMultimedia;
        } else {
            // Es texto
            HechoTexto hechoTexto = new HechoTexto(
                dto.getTitulo(),
                dto.getDescripcion(),
                categoria,
                ubicacion,
                dto.getFecha(),
                fuente
            );
            hechoTexto.informacion = dto.getDescripcion();
            hecho = hechoTexto;
        }
*/

        // Establecer la fecha de carga como la fecha actual (cuando se recibe)
        hecho.setFechaDeCarga(LocalDateTime.now());

        return hecho;
    }
    public Hecho adaptarDesdeFuenteProxy(HechoFuenteProxyDTO dto, Fuente fuente){
        Categoria categoria = this.repoCategoria.findByNombre(dto.getCategoria());
        if(categoria == null) {
            categoria = new Categoria();
            categoria.setNombre(dto.getCategoria());
            categoria = this.repoCategoria.save(categoria);
        }

        Ubicacion ubicacion = new Ubicacion(Float.valueOf( dto.getUbicacionLat()),Float.valueOf(dto.getUbicacionLon())); //Chequear orden

        Hecho hecho = new Hecho(
                dto.getTitulo(),
                dto.getDescripcion(),
                categoria,
                ubicacion,
                LocalDate.parse(dto.getFecha()),
                fuente
        );
        return hecho;
    }

    public List<Hecho> adaptarHechosDeFuenteProxy(List<HechoFuenteProxyDTO> hechosDTO) {

        Fuente fuente = this.repoFuente.findByTipoFuente(EnumTipoFuente.METAMAPA);
        if (fuente == null) {
            fuente = new Fuente();
            fuente.setTipoFuente(EnumTipoFuente.METAMAPA);
            fuente.setUrl(this.proxyUrl);
            fuente.setNombre("Proxy");
            fuente = this.repoFuente.save(fuente);
        }

        List<Hecho> hechos = new ArrayList<>();
        for (HechoFuenteProxyDTO hechoFuenteProxyDTO : hechosDTO) {
            Hecho h = adaptarDesdeFuenteProxy(hechoFuenteProxyDTO, fuente);
            hechos.add(h);
        }
        return hechos;
    }

    public Hecho adaptarDesdeFuenteEstatica(HechoFuenteEstaticaDTO dto, Fuente fuente) {
        Categoria categoria = this.repoCategoria.findByNombre(dto.getCategoria().getNombre());
        if(categoria == null) {
            categoria = new Categoria();
            categoria.setNombre(dto.getCategoria().getNombre());
            categoria = this.repoCategoria.save(categoria);
        }

        Ubicacion ubicacion = null;
        if (dto.getUbicacion() != null) {
            ubicacion = new Ubicacion(
                    dto.getUbicacion().getLatitud(),
                    dto.getUbicacion().getLongitud()
            );
        }

        Hecho hecho = new Hecho(
                dto.getTitulo(),
                dto.getDescripcion(),
                categoria,
                ubicacion,
                dto.getFecha(),
                fuente
        );

        hecho.setFechaDeCarga(LocalDateTime.now());
        return hecho;
    }

    public List<Hecho> adaptarHechosDeFuenteDinamica(List<HechoFuenteDinamicaDTO> hechosDTO) {
        Fuente fuente = this.repoFuente.findByTipoFuente(EnumTipoFuente.DINAMICA);
        if (fuente == null) {
            fuente = new Fuente();
            fuente.setTipoFuente(EnumTipoFuente.DINAMICA);
            fuente.setUrl(this.dinamicaUrl);
            fuente.setNombre("Dinamica");
            fuente = this.repoFuente.save(fuente);
        }
        List<Hecho> hechos = new ArrayList<>();
        for (HechoFuenteDinamicaDTO hechoFuenteDinamicaDTO : hechosDTO) {
            Hecho h = adaptarDesdeFuenteDinamica(hechoFuenteDinamicaDTO, fuente);
            hechos.add(h);
        }
        return hechos;
    }

    public List<Hecho> adaptarHechosDeFuenteEstatica(String ruta, List<HechoFuenteEstaticaDTO> hechosDTO){
        Fuente fuente = this.repoFuente.findByUrl(ruta);
        if(fuente == null) {
            return new ArrayList<>();
        }
        List<Hecho> hechos = new ArrayList<>();
        for(int i = 0; i < hechosDTO.size(); i++){
            Hecho h = adaptarDesdeFuenteEstatica(hechosDTO.get(i), fuente);
            hechos.add(h);
        }
        return hechos;
    }


}
