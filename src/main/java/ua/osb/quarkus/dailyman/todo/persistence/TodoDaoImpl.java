package ua.osb.quarkus.dailyman.todo.persistence;

import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@ApplicationScoped
class TodoDaoImpl implements TodoDao {
    private final EntityManager entityManager;

    @Override
    public Optional<TodoEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(TodoEntity.class, id));
    }

    @Override
    @Transactional
    public TodoEntity create(TodoEntity _new) {
        entityManager.persist(_new);
        return _new;
    }

    @Override
    @Transactional
    public TodoEntity update(TodoEntity updated) {
        return entityManager.merge(updated);
    }

    @Override
    public List<TodoEntity> findAll() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TodoEntity> todoCriteriaQuery = criteriaBuilder.createQuery(TodoEntity.class);
        Root<TodoEntity> todoRoot = todoCriteriaQuery.from(TodoEntity.class);
        todoCriteriaQuery.select(todoRoot);

        TypedQuery<TodoEntity> query = entityManager.createQuery(todoCriteriaQuery);
        return query.getResultList();
    }
}
