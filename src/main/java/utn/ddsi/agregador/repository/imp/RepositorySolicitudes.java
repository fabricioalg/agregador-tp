package utn.ddsi.agregador.repository.imp;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;

@Getter
@Setter
@Repository
public class RepositorySolicitudes {

/*
    public void save(SolicitudEliminacion solicitud, Hecho hecho) {
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
    }*/
}
