package utn.ddsi.agregador.domain;

import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

public interface InterfaceCondicion {
    public boolean cumpleCondicion(Hecho hecho);
}

