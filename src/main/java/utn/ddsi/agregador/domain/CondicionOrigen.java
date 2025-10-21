package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

public class CondicionOrigen implements InterfaceCondicion {
    private String origen;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getOrigen().equals(this.origen);
    }
}
