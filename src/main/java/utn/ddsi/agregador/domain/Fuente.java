package utn.ddsi.agregador.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.net.URL;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "fuente")
public abstract class Fuente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFuente;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private URL url;
    @Column(nullable = false)
    private EnumTipoFuente tipoFuente;

    public Fuente(long id, String nombre, URL url, EnumTipoFuente tipoFuente) {
        this.idFuente = id;
        this.nombre = nombre;
        this.url = url;
        this.tipoFuente = tipoFuente;
    }
}