package utn.ddsi.agregador.domain.hecho;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Embeddable
@NoArgsConstructor
public class Etiqueta {
    private String nombre;
    public Etiqueta(String nombre) {
        this.nombre = nombre;
    }
}
