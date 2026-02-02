package utn.ddsi.agregador.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import utn.ddsi.agregador.monitoring.healthindicators.DatabaseHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteDinamicaHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteEstaticaHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteProxyHealthIndicator;

@Service
public class DependencyMonitorService {

    private static final Logger log =
            LoggerFactory.getLogger(DependencyMonitorService.class);

    private final DatabaseHealthIndicator database;
    private final FuenteDinamicaHealthIndicator dinamica;
    private final FuenteEstaticaHealthIndicator estatica;
    private final FuenteProxyHealthIndicator proxy;

    public DependencyMonitorService(DatabaseHealthIndicator database,
                                    FuenteDinamicaHealthIndicator dinamica,
                                    FuenteEstaticaHealthIndicator estatica,
                                    FuenteProxyHealthIndicator proxy) {
        this.database = database;
        this.dinamica = dinamica;
        this.estatica = estatica;
        this.proxy = proxy;
    }

    @Scheduled(fixedDelay = 15000)
    public void heartbeat() {
        log.info("Heartbeat: monitoreo de dependencias activo");
    }
}
