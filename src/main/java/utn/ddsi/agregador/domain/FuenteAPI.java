package utn.ddsi.agregador.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
@Entity
public class FuenteAPI extends Fuente {
    public FuenteAPI() {
        super();
    }

    public FuenteAPI(long id, String nombre, URL url, EnumTipoFuente tipoFuente) {
        super(id, nombre, url, tipoFuente);
    }
}