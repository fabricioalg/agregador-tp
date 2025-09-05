package utn.ddsi.agregador.repository;

import utn.ddsi.agregador.domain.SolicitudEliminacion;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepositorySolicitudes {
    private List<SolicitudEliminacion> solicitudes;

    public RepositorySolicitudes() {
        this.solicitudes = new ArrayList<SolicitudEliminacion>();
    }

    public void save(SolicitudEliminacion solicitud) {solicitudes.add(solicitud);};
    public void delete(SolicitudEliminacion solicitud){solicitudes.remove(solicitud);};
}
