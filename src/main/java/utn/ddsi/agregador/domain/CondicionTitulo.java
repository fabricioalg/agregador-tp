package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

public class CondicionTitulo implements InterfaceCondicion {
    private String titulo;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getTitulo().equals(this.titulo);
    }
}
