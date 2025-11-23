package utn.ddsi.agregador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadisticaProviciaXCategoriaDTO {
    private String provincia;
    private Long cantidad;
}
