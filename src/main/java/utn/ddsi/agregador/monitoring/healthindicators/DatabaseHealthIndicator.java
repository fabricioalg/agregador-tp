package utn.ddsi.agregador.monitoring.healthindicators;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("database")
public class DatabaseHealthIndicator extends AbstractDependencyHealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected String dependencyName() {
        return "database";
    }

    @Override
    protected String downMessage() {
        return "Database no disponible";
    }

    @Override
    protected boolean estaDisponible() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        return result != null && result == 1;
    }
}
