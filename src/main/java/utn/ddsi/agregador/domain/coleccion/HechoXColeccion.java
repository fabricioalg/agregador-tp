package utn.ddsi.agregador.domain.coleccion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.ddsi.agregador.domain.hecho.Hecho;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hecho_x_coleccion")
public class HechoXColeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hecho_x_coleccion;

    @ManyToOne
    private Hecho hecho;

    @ManyToOne
    private Coleccion coleccion;

    private Boolean consensuado;

    public HechoXColeccion(Hecho hecho, Coleccion coleccion, Boolean consensuado) {
        this.hecho = hecho;
        this.coleccion = coleccion;
        this.consensuado = consensuado;
    }
}

