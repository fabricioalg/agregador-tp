package utn.ddsi.agregador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.ddsi.agregador.dto.EstadisticaSolicitudesDTO;
import utn.ddsi.agregador.repository.IRepositorySolicitudes;

import java.time.LocalDate;

@Service
public class ServiceSolicitudes {
    @Autowired
    private final IRepositorySolicitudes repoSolicitudes;

    public ServiceSolicitudes(IRepositorySolicitudes repositorySolicitudes){
        this.repoSolicitudes= repositorySolicitudes;
        
    }

    public EstadisticaSolicitudesDTO cantidadSolicitudesSpam(LocalDate fecha){
        Long cantidadSolitudesSpam= this.repoSolicitudes.contarSolicitudesSpamDesde(fecha);
        EstadisticaSolicitudesDTO estadistica = new EstadisticaSolicitudesDTO(cantidadSolitudesSpam);
        return null;
    };
    

}
