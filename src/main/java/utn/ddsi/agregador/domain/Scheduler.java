package utn.ddsi.agregador.domain;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private ServicioAgregador servicioAgregador;

    @Scheduled(cron = "0 0 0 * * *")
    public void avisarAlAgregador(){
        servicioAgregador.depurarHechos();
    }
}
