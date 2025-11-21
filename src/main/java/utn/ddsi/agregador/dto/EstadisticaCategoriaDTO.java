package utn.ddsi.agregador.dto;

public class EstadisticaCategoriaDTO {

   // private String id_categoria
    private String categoria;
    private Long cantidadHechos;

    public EstadisticaCategoriaDTO(Long cantidad,String categoria){
        this.categoria= categoria;
        this.cantidadHechos= cantidad;
    }
}
