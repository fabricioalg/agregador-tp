package utn.ddsi.agregador.domain.hecho;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float latitud;
    private float longitud;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provincia_id_provincia")
    private Provincia provincia;

    public Ubicacion(float latitud, float longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
