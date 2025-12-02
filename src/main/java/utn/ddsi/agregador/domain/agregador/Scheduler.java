package utn.ddsi.agregador.domain.agregador;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private final ActualizadorColecciones actualizador;

    public Scheduler(ActualizadorColecciones actualizador) {
        this.actualizador = actualizador;
    }

    // Ejecuta cada hora
    @Scheduled(fixedRate = 3600000)
    public void ejecutarActualizacionPeriodica() {
        try {
            actualizador.actualizarColecciones();
        } catch (Exception e) {
            System.err.println("[Scheduler] Error en actualización: " + e.getMessage());
        }
    }
    @Scheduled(cron = "0 0 1 * * *")
    public void ejecutarAlgoritmosDeConsenso() {
        try {
            actualizador.ejecutarAlgoritmosDeConsenso();
        } catch (Exception e) {
            System.err.println("[Scheduler] Error en actualización: " + e.getMessage());
        }
    }
}