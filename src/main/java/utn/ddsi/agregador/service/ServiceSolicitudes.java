package utn.ddsi.agregador.service;

import utn.ddsi.agregador.dto.EstadisticaSolicitudesDTO;
import utn.ddsi.agregador.repository.IRepositorySolicitudes;

import java.time.LocalDate;

public class ServiceSolicitudes {
    
    private final IRepositorySolicitudes repoSolicitudes;

    public ServiceSolicitudes(IRepositorySolicitudes repositorySolicitudes){
        this.repoSolicitudes= repositorySolicitudes;
        
    }

    public EstadisticaSolicitudesDTO cantidadSolicitudesSpam(LocalDate fecha){
        Long cantidadSolitudesSpam= this.repoSolicitudes.contarSolicitudesSpamDesde(fecha);
        EstadisticaSolicitudesDTO estadistica = new EstadisticaSolicitudesDTO(cantidadSolitudesSpam);
        return estadistica;
    };
    

}
