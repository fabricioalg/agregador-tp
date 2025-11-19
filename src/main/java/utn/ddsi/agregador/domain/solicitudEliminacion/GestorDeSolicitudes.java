package utn.ddsi.agregador.domain.solicitudEliminacion;

import org.springframework.stereotype.Component;
import utn.ddsi.agregador.repository.IRepositorySolicitudes;
import utn.ddsi.agregador.utils.EnumEstadoSol;

@Component
public class GestorDeSolicitudes {
    private DetectorDeSpam detector;
    private IRepositorySolicitudes repository;

    public GestorDeSolicitudes(IRepositorySolicitudes repository) {
        this.repository = repository;
        this.detector = new DetectorBasicoDeSpam();
    }

    public void procesarTodasLasSolicitudes() {
        repository.findAll().forEach(this::procesarSolicitud);
    }
    public void procesarSolicitud(SolicitudEliminacion solicitud) {
        if (detector.esSpam(solicitud.getMotivo())) {
            solicitud.setEstado(EnumEstadoSol.RECHAZADA);
            repository.save(solicitud);
        } else {
            solicitud.setEstado(EnumEstadoSol.PENDIENTE);
            repository.save(solicitud);
        }
    }
}