package utn.ddsi.agregador.monitoring.healthindicators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

public abstract class AbstractDependencyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            if (estaDisponible()) {
                return Health.up()
                        .withDetail(dependencyName(), "OK")
                        .build();
            }
           // return Health.status(200).withDetail(dependencyName(), downMessage())
             //       .build();
            return Health.status(Status.DOWN).build();

        } catch (Exception ex) {
            //return Health.down()
              //      .withDetail(dependencyName(), downMessage())
                  //  .withException(ex)
                //    .build();
                return Health.status(Status.DOWN).build();
        }
    }

    protected abstract String dependencyName();
    protected abstract String downMessage();

    public abstract boolean estaDisponible();

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
