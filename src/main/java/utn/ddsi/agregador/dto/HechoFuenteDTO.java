package utn.ddsi.agregador.dto;

import lombok.Getter;

@Getter
public class HechoFuenteDTO {
    private final Long idHecho;
    private final String titulo;
    private final String descripcion;
    private final String urlFuente;

    public HechoFuenteDTO(Long idHecho, String titulo, String descripcion, String urlFuente) {
        this.idHecho = idHecho;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlFuente = urlFuente;
    }

}