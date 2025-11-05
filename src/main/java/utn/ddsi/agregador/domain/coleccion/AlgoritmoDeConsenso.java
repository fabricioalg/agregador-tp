package utn.ddsi.agregador.domain.coleccion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.domain.fuentes.Fuente;
import utn.ddsi.agregador.domain.hecho.Hecho;
import utn.ddsi.agregador.utils.EnumTipoDeAlgoritmo;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AlgoritmoDeConsenso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_algoritmo;


    List<Hecho> aplicar(List<Hecho>hechos, List<Fuente> fuentes){
        return null;
    };

}
