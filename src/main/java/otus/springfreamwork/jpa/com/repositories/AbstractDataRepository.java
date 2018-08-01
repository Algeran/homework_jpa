package otus.springfreamwork.jpa.com.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
abstract class AbstractDataRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;
    private Class<T> entityClass;

    AbstractDataRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T getById(int id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> getAll() {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
        query.select(query.from(entityClass));
        return entityManager.createQuery(query).getResultList();
    }

    public long count() {
        CriteriaQuery<Long> query = entityManager.getCriteriaBuilder().createQuery(Long.class);
        query.select(entityManager.getCriteriaBuilder().count(query.from(entityClass)));
        return entityManager.createQuery(query).getSingleResult();
    }

    public void insert(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        T mergedEntity = entityManager.merge(entity);
        entityManager.detach(mergedEntity);
        return mergedEntity;
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void detach(T entity) {
        entityManager.detach(entity);
    }

    EntityManager getEntityManager() {
        return entityManager;
    }
}
