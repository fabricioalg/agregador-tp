package utn.ddsi.agregador.domain.solicitudEliminacion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.domain.entities.condicion.hecho.Hecho;
import utn.ddsi.agregador.utils.EnumEstadoSol;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Solicitud")
@NoArgsConstructor
public class SolicitudEliminacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;
    @OneToOne
    private Hecho hecho;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(length = 200)
    private String motivo;
    @Column(nullable = false)
    private EnumEstadoSol estado;

    public SolicitudEliminacion(Hecho hecho, LocalDate fecha, String motivo) {
        this.hecho = hecho;
        this.fecha = fecha;
        this.motivo = motivo;
        this.estado = EnumEstadoSol.PENDIENTE;
    }
}