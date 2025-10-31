package utn.ddsi.agregador.domain.hecho;

import utn.ddsi.agregador.domain.fuentes.Fuente;

import java.time.LocalDate;

public class HechoMultimedia extends Hecho {
    public String rutaAlContenido;

    public HechoMultimedia(String titulo, String descripcion, Categoria categoria, Ubicacion lugarDeOcurrencia, LocalDate fecha, Fuente fuente) {
        super(
                titulo,
                descripcion,
                categoria,
                lugarDeOcurrencia,
                fecha,
                fuente
        );
    }
}
