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

    public ServiceSolicitudes(IRepositorySolicitudes repoSolicitudes) {
        this.repoSolicitudes = repoSolicitudes;
    }

    public EstadisticaSolicitudesDTO cantidadSolicitudesSpam(){
        EstadisticaSolicitudesDTO estadistica= this.repoSolicitudes.obtenerEstadisticas();
        return estadistica;
    };
    

}
