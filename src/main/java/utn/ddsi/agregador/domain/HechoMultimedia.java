package utn.ddsi.agregador.domain;

import java.time.LocalDate;

public class HechoMultimedia extends Hecho {
    public String rutaAlContenido;

    public HechoMultimedia(String titulo, String descripcion, Categoria categoria,Ubicacion lugarDeOcurrencia, LocalDate fecha) {
        super(
                titulo,
                descripcion,
                categoria,
                lugarDeOcurrencia,
                fecha
        );
    }
}
