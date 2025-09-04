package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.net.URL;

@Getter
@Setter
public abstract class Fuente {
    private long id;
    private String nombre;
    private URL url;
    private EnumTipoFuente tipoFuente;

    public Fuente(long id, String nombre, URL url, EnumTipoFuente tipoFuente) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        this.tipoFuente = tipoFuente;
    }
}