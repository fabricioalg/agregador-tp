package utn.ddsi.agregador.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Ubicacion {
    private float latitud;
    private float longitud;

    public Ubicacion(float latitud, float longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void setUbicacion(float newLatitud, float newLongitud) {
        this.latitud = newLatitud;
        this.longitud = newLongitud;
    }

    public float getLatitud() {return this.latitud;}

    public float getLongitud() {
        return longitud;
    }
}
