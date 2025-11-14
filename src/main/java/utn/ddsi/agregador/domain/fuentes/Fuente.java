package utn.ddsi.agregador.domain.fuentes;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsi.agregador.utils.EnumTipoFuente;

import java.net.URL;

@Getter
@Setter
@Entity
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
        this.nombre = nombre;
        this.url = url;
        this.tipoFuente = tipoFuente;
    }
    public Fuente() {
    }
    public long getIdFuente() {
        return idFuente;
    }
    public void setIdFuente(long idFuente) {
        this.idFuente = idFuente;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUrl() {
        return url;}
    public void setUrl(String url) {
        this.url = url;
    }
    public EnumTipoFuente getTipoFuente() {
        return tipoFuente;
    }
    public void setTipoFuente(EnumTipoFuente tipoFuente) {
        this.tipoFuente = tipoFuente;}

}