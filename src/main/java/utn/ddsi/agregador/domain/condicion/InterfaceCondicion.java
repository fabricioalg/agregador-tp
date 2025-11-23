package utn.ddsi.agregador.domain.condicion;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import utn.ddsi.agregador.domain.hecho.Hecho;

@Data
@Entity
@Table(name = "condicion")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CondicionCategoria.class, name = "categoria"),
        @JsonSubTypes.Type(value = CondicionEtiqueta.class, name = "etiqueta"),
        @JsonSubTypes.Type(value = CondicionFechaANTES.class, name = "fechaAntes"),
        @JsonSubTypes.Type(value = CondicionFechaDESPUES.class, name = "fechaDespues"),
        @JsonSubTypes.Type(value = CondicionFuente.class, name = "fuente"),
        @JsonSubTypes.Type(value = CondicionTitulo.class, name = "titulo")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Condicion", discriminatorType = DiscriminatorType.STRING)
public abstract class InterfaceCondicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_condicion;

    public InterfaceCondicion() {}

    public boolean cumpleCondicion(Hecho hecho) {
        return false;
    }
}

