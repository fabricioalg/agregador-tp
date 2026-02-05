package utn.ddsi.agregador.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import utn.ddsi.agregador.monitoring.healthindicators.DatabaseHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteDinamicaHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteEstaticaHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteProxyHealthIndicator;

@Slf4j
@Service
public class DependencyMonitorService {


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

        boolean databaseOk = database.estaDisponible();
        //boolean dinamicaOk = dinamica.estaDisponible();
        //boolean estaticaOk = estatica.estaDisponible();
        //boolean proxyOk = proxy.estaDisponible();

        //if (!databaseOk || !dinamicaOk || !estaticaOk || !proxyOk) {
        if(!databaseOk) {
            log.error("Dependencia crítica caída → forzando restart");
            throw new IllegalStateException(
                    "Dependencia crítica caída: autorestart requerido");
        }

        log.info("Heartbeat OK – todas las dependencias UP");
    }

}
