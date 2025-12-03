package utn.ddsi.agregador.dto;

import lombok.Getter;

@Getter
public class HechoFuenteDTO {
    private final Long idHecho;
    private final Long idFuente;

    public HechoFuenteDTO(Long idHecho, Long idFuente) {
        this.idHecho = idHecho;
        this.idFuente = idFuente;
    }

}