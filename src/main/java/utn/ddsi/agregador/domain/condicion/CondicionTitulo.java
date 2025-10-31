package utn.ddsi.agregador.domain.condicion;

import utn.ddsi.agregador.domain.hecho.Hecho;

public class CondicionTitulo extends InterfaceCondicion {
    private String titulo;

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getTitulo().equals(this.titulo);
    }
}
