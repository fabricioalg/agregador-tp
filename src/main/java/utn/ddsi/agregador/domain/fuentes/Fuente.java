package utn.ddsi.agregador.domain.fuentes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import java.net.URL;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "fuente")
/*@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FuenteAPI.class, name = "API")
})*/
public class Fuente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFuente;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String url;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumTipoFuente tipoFuente;

    public Fuente(long id, String nombre, String url, EnumTipoFuente tipoFuente) {
        this.idFuente = id;
        this.nombre = nombre;
        this.url = url;
        this.tipoFuente = tipoFuente;
    }
}