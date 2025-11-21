package utn.ddsi.agregador.dto;

import lombok.Data;

@Data
public class EstadisticaSolicitudesDTO {
    private Long cantidadSolicitudes;

    public EstadisticaSolicitudesDTO(Long cantidad){
        this.cantidadSolicitudes = cantidad;
    }

}
