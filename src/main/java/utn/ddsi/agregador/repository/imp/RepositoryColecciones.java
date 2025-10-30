package utn.ddsi.agregador.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import utn.ddsi.agregador.domain.models.coleccion.Coleccion;
import utn.ddsi.agregador.domain.schemas.ColeccionSchema;
import utn.ddsi.agregador.domain.schemas.HechoSchema;
import utn.ddsi.agregador.utils.BDUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;

@Repository
public class RepositoryColecciones extends JpaRepository<ColeccionSchema, Long> {
    private final EntityManager em = BDUtils.getEntityManager();

    public Optional<Coleccion> buscarPorNombre(String titulo) {
        return Optional.ofNullable(colecciones.get(titulo));
    }
    public List<Coleccion> obtenerTodasLasColecciones() {
        return new ArrayList<>(colecciones.values());
    }

    private Coleccion toDTO(ColeccionSchema schema) {
        return new Coleccion(schema.getTitulo(), schema.getDescripcion());
    }

    private ColeccionSchema fromDTO(Coleccion coleccion) {
        ColeccionSchema schema = new ColeccionSchema();
        schema.setTitulo(coleccion.getTitulo());
        schema.setDescripcion(coleccion.getDescripcion());

        return schema;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ColeccionSchema> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ColeccionSchema> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<ColeccionSchema> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ColeccionSchema getOne(Long aLong) {
        return null;
    }

    @Override
    public ColeccionSchema getById(Long aLong) {
        return null;
    }

    @Override
    public ColeccionSchema getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ColeccionSchema> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ColeccionSchema> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends ColeccionSchema> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends ColeccionSchema> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ColeccionSchema> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ColeccionSchema> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ColeccionSchema, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ColeccionSchema> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ColeccionSchema> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<ColeccionSchema> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<ColeccionSchema> findAll() {
        return List.of();
    }

    @Override
    public List<ColeccionSchema> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(ColeccionSchema entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ColeccionSchema> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ColeccionSchema> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<ColeccionSchema> findAll(Pageable pageable) {
        return null;
    }
}
