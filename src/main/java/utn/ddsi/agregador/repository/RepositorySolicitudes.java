package utn.ddsi.agregador.repository;

import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.models.hecho.Hecho;
import utn.ddsi.agregador.domain.models.solicitudEliminacion.SolicitudEliminacion;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import utn.ddsi.agregador.domain.schemas.HechoSchema;
import utn.ddsi.agregador.domain.schemas.SolicitudEliminacionSchema;
import utn.ddsi.agregador.utils.BDUtils;

import javax.persistence.EntityManager;

@Getter
@Setter
@Repository
public class RepositorySolicitudes {

    private final EntityManager em = BDUtils.getEntityManager();

    public void save(SolicitudEliminacion solicitud, HechoSchema hecho) {
        SolicitudEliminacionSchema ses = new SolicitudEliminacionSchema();
        ses.setEstado(solicitud.getEstado());
        ses.setFecha(solicitud.getFecha());
        ses.setMotivo(solicitud.getMotivo());
        ses.setHecho(hecho);
        em.persist(ses);
    };

    public void delete(SolicitudEliminacion solicitud){
        SolicitudEliminacionSchema ses = em.find(SolicitudEliminacionSchema.class, solicitud.getId());
        if (ses != null) {
            em.getTransaction().begin();
            em.remove(ses);
            em.getTransaction().commit();
        }
    }
}
