package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SolicitudEliminacion {

    private Hecho hecho;
    private LocalDate fecha;
    private String motivo;
    private EnumEstadoSol estado;

    public SolicitudEliminacion(Hecho hecho, LocalDate fecha, String motivo) {
        this.hecho = hecho;
        this.fecha = fecha;
        this.motivo = motivo;
    }
}