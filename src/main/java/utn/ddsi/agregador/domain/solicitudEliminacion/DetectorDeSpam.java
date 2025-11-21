package utn.ddsi.agregador.domain.solicitudEliminacion;

import org.springframework.stereotype.Component;

@Component
public interface DetectorDeSpam {
    boolean esSpam(String texto);
}