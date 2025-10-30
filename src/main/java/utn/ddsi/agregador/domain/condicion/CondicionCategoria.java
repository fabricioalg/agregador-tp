package utn.ddsi.agregador.domain.condicion;

import utn.ddsi.agregador.domain.hecho.Categoria;
import utn.ddsi.agregador.domain.hecho.Hecho;

public class CondicionCategoria implements InterfaceCondicion {
    private Categoria categoria;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getCategoria().getNombre().equals(this.categoria.getNombre());
    }
}
