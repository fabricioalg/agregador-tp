package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

public class CondicionCategoria implements InterfaceCondicion {
    private Categoria categoria;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getCategoria().getNombre().equals(this.categoria.getNombre());
    }
}
