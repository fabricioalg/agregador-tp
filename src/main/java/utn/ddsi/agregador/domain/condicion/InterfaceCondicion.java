package utn.ddsi.agregador.domain.condicion;

import jakarta.persistence.*;
import lombok.Data;
import utn.ddsi.agregador.domain.hecho.Hecho;

@Data
@Entity
@Table(name = "condicion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)// DISCUTIR POR WHASATP
@DiscriminatorColumn(name = "tipo") // columna automática en la BD
public abstract class InterfaceCondicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public boolean cumpleCondicion(Hecho hecho) {
        return false;
    }
}

