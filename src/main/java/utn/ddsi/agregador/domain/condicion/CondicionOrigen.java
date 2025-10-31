package utn.ddsi.agregador.domain.condicion;

import utn.ddsi.agregador.domain.hecho.Hecho;

public class CondicionOrigen extends InterfaceCondicion {
    private String origen;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getFuente().getTipoFuente().toString().equals(this.origen);
    }
}
