package utn.ddsi.agregador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadisticaColeccionHechosXProvinciaDTO {

    private String provincia;
    private Long cantidadDeReportados;

}