package utn.ddsi.agregador.domain;

import java.time.LocalDate;

public class HechoTexto extends Hecho{
    public String informacion;

    public HechoTexto(String titulo, String descripcion, Categoria categoria, Ubicacion ubicacion, LocalDate fecha, Fuente fuente) {
        super(titulo, descripcion, categoria, ubicacion, fecha, fuente);
    }
}
