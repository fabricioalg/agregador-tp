package utn.ddsi.agregador.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    // Bean unificado de RestTemplate para hacer peticiones HTTP
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build(); // hace peticiones HTTP igual que antes + soporte de trazas
    }
}

//    @Bean
//    public OpenTelemetryAppender otelLogAppender(OpenTelemetry openTelemetry) {
//        OpenTelemetryAppender appender = new OpenTelemetryAppender();
//        appender.install(openTelemetry); // Aquí es donde ocurre la magia de unión
//        return appender;
//    }
