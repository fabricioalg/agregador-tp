package utn.ddsi.agregador.domain.agregador;

import org.springframework.scheduling.annotation.Scheduled;

public class Scheduler {
    private ServicioAgregador servicioAgregador;

    @Scheduled(cron = "0 0 0 * * *")
    public void avisarAlAgregador(){
        servicioAgregador.depurarHechos();
    }
}
