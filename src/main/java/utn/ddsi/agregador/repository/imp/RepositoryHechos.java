package utn.ddsi.agregador.repository.imp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Getter
@Setter
@Repository
public class RepositoryHechos {

   /* @Transactional
    public Hecho save(Hecho hecho) {
        HechoSchema hechoSchema = fromDTO(hecho);
        em.persist(hechoSchema);
        return hecho;
    }

    public Optional<Hecho> findById(Long id) {
        HechoSchema schema = em.find(HechoSchema.class, id);
        return schema != null ? Optional.of(toDTO(schema)) : Optional.empty();
    }

    public List<Hecho> findByTitulo(String titulo) {
        List<HechoSchema> q = em.createQuery(
                "SELECT h FROM Hecho h WHERE lower(h.titulo) = :titulo", HechoSchema.class)
                .setParameter("titulo", titulo)
                .getResultList();
        return toDTOs(q);
    }

    /*private List<Hecho> toDTOs(List<HechoSchema> schemas) {
        List<Hecho> hechos = new ArrayList<>();
        for (HechoSchema schema : schemas) {
            hechos.add(toDTO(schema));
        }
        return hechos;
    }

    private List<HechoSchema> fromDTOs(List<Hecho> hechos) {
        List<HechoSchema> schemas = new ArrayList<>();
        for (Hecho hecho : hechos) {
            schemas.add(fromDTO(hecho));
        }
        return schemas;
    }

    private Hecho toDTO(HechoSchema schema) {
        Hecho hecho = new Hecho();
        hecho.setTitulo(schema.getTitulo());
        hecho.setDescripcion(schema.getDescripcion());
        hecho.setFecha(schema.getFecha());
        hecho.setFechaDeCarga(schema.getFechaDeCarga());

        hecho.setFuente(new Fuente(schema.getOrigen().getNombre(),
                schema.getOrigen().getUrl(),
                schema.getOrigen().getTipoFuente().getNombre()));

        if (schema.getCategoria() != null)
            hecho.setCategoria(new Categoria(schema.getCategoria().getNombre()));

        if (schema.getUbicacion() != null)
            hecho.setUbicacion(new Ubicacion(
                    schema.getUbicacion().getLatitud(),
                    schema.getUbicacion().getLongitud()
            ));

        if (schema.getEtiqueta() != null)
            hecho.setEtiqueta(new Etiqueta(schema.getEtiqueta().getNombre()));

        return hecho;
    }


    private HechoSchema fromDTO(Hecho hecho) {
        HechoSchema schema = new HechoSchema();
        schema.setTitulo(hecho.getTitulo());
        schema.setDescripcion(hecho.getDescripcion());
        schema.setFecha(hecho.getFecha());
        schema.setFechaDeCarga(hecho.getFechaDeCarga());

        List<FuenteSchema> fuente = em.createQuery(
                "SELECT f FROM Fuente f WHERE LOWER(f.nombre) = LOWER(:nombre) and LOWER(f.url) = LOWER(:url) and LOWER(f.tipo) = LOWER(:tipo)" , FuenteSchema.class)
                .setParameter("nombre", hecho.getFuente().getNombre())
                .setParameter("url", hecho.getFuente().getUrl())
                .setParameter("tipo", hecho.getFuente().getTipoFuente().toString())
                .getResultList();

        if(fuente.isEmpty()){
            FuenteSchema nuevaFuente = new FuenteSchema();
            nuevaFuente.setNombre(hecho.getFuente().getNombre());
            nuevaFuente.setUrl(hecho.getFuente().getUrl());
            TipoFuenteSchema tipo = em.createQuery(
                    "SELECT tf FROM TipoFuente tf WHERE LOWER(tf.nombre) = LOWER(:nombre)",
                    TipoFuenteSchema.class)
                    .setParameter("nombre", hecho.getFuente().getTipoFuente().toString())
                    .getSingleResult();
            if(tipo == null){
                tipo = new TipoFuenteSchema();
                tipo.setNombre(hecho.getFuente().getTipoFuente().toString());
                em.persist(tipo);
            }
            nuevaFuente.setTipoFuente(tipo);
            em.persist(nuevaFuente);
            schema.setOrigen(nuevaFuente);
        } else {
            schema.setOrigen(fuente.get(0));
        }

        CategoriaSchema categoria = em.createQuery(
                        "SELECT c FROM Categoria c WHERE LOWER(c.nombre) = LOWER(:nombre)",
                        CategoriaSchema.class)
                .setParameter("nombre", hecho.getCategoria().getNombre())
                .getResultStream()
                .findFirst()
                .orElseGet(() -> {
                    CategoriaSchema nueva = new CategoriaSchema();
                    nueva.setNombre(hecho.getCategoria().getNombre());
                    em.persist(nueva);
                    return nueva;
                });
        schema.setCategoria(categoria);

        UbicacionSchema ubicacion = new UbicacionSchema();
        ubicacion.setLatitud(hecho.getUbicacion().getLatitud());
        ubicacion.setLongitud(hecho.getUbicacion().getLongitud());
        em.persist(ubicacion);
        schema.setUbicacion(ubicacion);

        if (hecho.getEtiqueta() != null) {
            EtiquetaSchema etiqueta = em.createQuery(
                            "SELECT e FROM Etiqueta e WHERE LOWER(e.nombre) = LOWER(:nombre)",
                            EtiquetaSchema.class)
                    .setParameter("nombre", hecho.getEtiqueta().getNombre())
                    .getResultStream()
                    .findFirst()
                    .orElseGet(() -> {
                        EtiquetaSchema nueva = new EtiquetaSchema();
                        nueva.setNombre(hecho.getEtiqueta().getNombre());
                        em.persist(nueva);
                        return nueva;
                    });
            schema.setEtiqueta(etiqueta);
        }

        return schema;
    }*/

}
