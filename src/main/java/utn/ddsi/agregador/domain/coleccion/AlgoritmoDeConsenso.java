package utn.ddsi.agregador.domain.coleccion;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.utils.EnumTipoDeAlgoritmo;

import java.util.List;

@Entity
@NoArgsConstructor
public class AlgoritmoDeConsenso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_algoritmo")
    private EnumTipoDeAlgoritmo tipo ;

    List<Hecho> aplicar(List<Hecho>hechos, List<Fuente> fuentes){
        return null;
    };

}
