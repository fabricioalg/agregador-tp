package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Categoria {
    @Getter @Setter private String nombre;
    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}
