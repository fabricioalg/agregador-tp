package utn.ddsi.agregador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstadisticaCategoriaDTO {

   // private String id_categoria
    private String categoria;
    private Long cantidadHechos;

    public EstadisticaCategoriaDTO(String categoria,Long cantidad){
        this.categoria= categoria;
        this.cantidadHechos= cantidad;
    }
}
