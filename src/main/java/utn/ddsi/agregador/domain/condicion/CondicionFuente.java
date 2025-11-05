package utn.ddsi.agregador.domain.condicion;

import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;

public class CondicionFuente extends InterfaceCondicion {
    private Fuente fuente;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return fuente.equals(hecho.getFuente());
    }
}
