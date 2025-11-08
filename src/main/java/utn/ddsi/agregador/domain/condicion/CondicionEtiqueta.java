package utn.ddsi.agregador.domain.condicion;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.domain.hecho.Hecho;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("etiqueta")
public class CondicionEtiqueta extends InterfaceCondicion {
    private String nombre;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getEtiqueta().getNombre().equals(nombre);
    }
}
