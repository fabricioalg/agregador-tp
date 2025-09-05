package utn.ddsi.agregador.repository;

import utn.ddsi.agregador.domain.Hecho;

import java.util.ArrayList;
import java.util.List;

public class RepositoryHechos {
    private List<Hecho> hechos;

    public RepositoryHechos() {
        this.hechos = new ArrayList<Hecho>();
    }

    public void save(Hecho hecho) {hechos.add(hecho);}
    public void saveAll(List<Hecho> muchosHechos) {hechos.addAll(muchosHechos);}
    public void delete(Hecho hecho){hechos.remove(hecho);}
}
