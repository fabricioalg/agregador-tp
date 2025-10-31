package utn.ddsi.agregador.domain.condicion;

import utn.ddsi.agregador.domain.hecho.Etiqueta;
import utn.ddsi.agregador.domain.hecho.Hecho;

public class CondicionEtiqueta extends InterfaceCondicion {
    private Etiqueta etiqueta;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getEtiqueta().getNombre().equals(this.etiqueta.getNombre());
    }
}
