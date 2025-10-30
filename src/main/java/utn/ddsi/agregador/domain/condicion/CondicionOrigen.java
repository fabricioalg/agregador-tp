package utn.ddsi.agregador.domain.condicion;

import utn.ddsi.agregador.domain.hecho.Hecho;

public class CondicionOrigen implements InterfaceCondicion {
    private String origen;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getOrigen().equals(this.origen);
    }
}
