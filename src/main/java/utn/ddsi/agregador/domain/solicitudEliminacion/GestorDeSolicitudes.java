package utn.ddsi.agregador.domain.solicitudEliminacion;

import utn.ddsi.agregador.repository.IRepositorySolicitudes;

public class GestorDeSolicitudes {
    private DetectorDeSpam detector;
    private IRepositorySolicitudes repository;

    public GestorDeSolicitudes(IRepositorySolicitudes repository) {
        this.repository = repository;
        this.detector = new DetectorBasicoDeSpam();
    }

    public void procesarTodasLasSolicitudes(){
        repository.findAll().forEach(solicitud -> this.procesarSolicitud(solicitud.getMotivo()));
    }
    public void procesarSolicitud(String textoSolicitud) {
        if (detector.esSpam(textoSolicitud)) {
            System.out.println("❌ Solicitud rechazada automáticamente por ser SPAM.");
        } else {
            System.out.println("✅ Solicitud válida. Pasará a revisión.");
        }
    }
}