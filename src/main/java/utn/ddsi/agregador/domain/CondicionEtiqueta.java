package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

public class CondicionEtiqueta implements InterfaceCondicion {
    private Etiqueta etiqueta;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getEtiqueta().getNombre().equals(this.etiqueta.getNombre());
    }
}
