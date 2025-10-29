package utn.ddsi.agregador.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Etiqueta {
    private String nombre;
    public Etiqueta(String nombre) {
        this.nombre = nombre;
    }
}
