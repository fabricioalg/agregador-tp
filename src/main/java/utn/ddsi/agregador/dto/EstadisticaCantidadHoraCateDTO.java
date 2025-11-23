package utn.ddsi.agregador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadisticaCantidadHoraCateDTO {
    private Integer hora;
    private Long cantidad;

}
