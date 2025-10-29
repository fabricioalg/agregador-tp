package utn.ddsi.agregador.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Categoria {
    private String nombre;
    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}
