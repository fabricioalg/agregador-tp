package utn.ddsi.agregador.monitoring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.ddsi.agregador.monitoring.healthindicators.DatabaseHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteDinamicaHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteEstaticaHealthIndicator;
import utn.ddsi.agregador.monitoring.healthindicators.FuenteProxyHealthIndicator;

@RestController
@RequestMapping("/monitor")
public class MonitorAdminController {

    private final DatabaseHealthIndicator database;
    private final FuenteDinamicaHealthIndicator dinamica;
    private final FuenteEstaticaHealthIndicator estatica;
    private final FuenteProxyHealthIndicator proxy;

    public MonitorAdminController(
            DatabaseHealthIndicator database,
            FuenteDinamicaHealthIndicator dinamica,
            FuenteEstaticaHealthIndicator estatica,
            FuenteProxyHealthIndicator proxy) {

        this.database = database;
        this.dinamica = dinamica;
        this.estatica = estatica;
        this.proxy = proxy;
    }

    // ---------- FALLAS ----------

    @PostMapping("/fail/database")
    public void failDatabase() {
        database.markDown();
    }

    @PostMapping("/fail/fuente-dinamica")
    public void failFuenteDinamica() {
        dinamica.markDown();
    }

    @PostMapping("/fail/fuente-estatica")
    public void failFuenteEstatica() {
        estatica.markDown();
    }

    @PostMapping("/fail/fuente-proxy")
    public void failFuenteProxy() {
        proxy.markDown();
    }

    // ---------- RECUPERACIÓN ----------

    @PostMapping("/recover/all")
    public void recoverAll() {
        database.markUp();
        dinamica.markUp();
        estatica.markUp();
        proxy.markUp();
    }
}
