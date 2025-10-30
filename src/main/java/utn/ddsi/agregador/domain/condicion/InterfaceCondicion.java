package utn.ddsi.agregador.domain.condicion;

import utn.ddsi.agregador.domain.hecho.Hecho;

public interface InterfaceCondicion {
    public boolean cumpleCondicion(Hecho hecho);
}

