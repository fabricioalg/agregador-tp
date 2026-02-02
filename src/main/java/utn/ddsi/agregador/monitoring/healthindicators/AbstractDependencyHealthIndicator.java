package utn.ddsi.agregador.monitoring.healthindicators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public abstract class AbstractDependencyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            if (estaDisponible()) {
                return Health.up()
                        .withDetail(dependencyName(), "OK")
                        .build();
            }
            return Health.down()
                    .withDetail(dependencyName(), downMessage())
                    .build();
        } catch (Exception ex) {
            return Health.down()
                    .withDetail(dependencyName(), downMessage())
                    .withException(ex)
                    .build();
        }
    }

    protected abstract String dependencyName();
    protected abstract String downMessage();

    protected abstract boolean estaDisponible();

    public Health markDown() {
        return Health.down()
                .withDetail(dependencyName(), downMessage())
                .build();
    }

    public Health markUp() {
        return Health.up()
                .withDetail(dependencyName(), "OK")
                .build();
    }
}
