package utn.ddsi.agregador.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class Hecho {
    //getters y setters
    private String Titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fecha;
    private LocalDate fechaDeCarga;
    private Fuente origen;
    private Ubicacion lugarDeOcurrencia;
    private Etiqueta etiqueta;

    public Hecho(String titulo, String descripcion, Categoria categoria,Ubicacion lugarDeOcurrencia, LocalDate fecha) {
        this.Titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.lugarDeOcurrencia = lugarDeOcurrencia;
        this.fecha = fecha;
        this.etiqueta = null;
    }
}
