package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Etiqueta {
    @Getter @Setter private String nombre;
    public Etiqueta(String nombre) {
        this.nombre = nombre;
    }
}
