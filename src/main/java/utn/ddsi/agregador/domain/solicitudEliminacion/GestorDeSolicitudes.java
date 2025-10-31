package utn.ddsi.agregador.domain.solicitudEliminacion;

import utn.ddsi.agregador.repository.imp.RepositorySolicitudes;

public class GestorDeSolicitudes {
    private DetectorDeSpam detector;
    private RepositorySolicitudes repository;

    public GestorDeSolicitudes() {
        this.detector = new DetectorBasicoDeSpam();
        this.repository = new RepositorySolicitudes();
    }
/*
    public void procesarTodasLasSolicitudes(){
        repository.getSolicitudes().forEach(solicitud -> this.procesarSolicitud(solicitud.getMotivo()));

    }
    public void procesarSolicitud(String textoSolicitud) {
        if (detector.esSpam(textoSolicitud)) {
            System.out.println("❌ Solicitud rechazada automáticamente por ser SPAM.");
        } else {
            System.out.println("✅ Solicitud válida. Pasará a revisión.");
        }
    }*/
}