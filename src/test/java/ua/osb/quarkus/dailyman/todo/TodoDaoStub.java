package ua.osb.quarkus.dailyman.todo;

import lombok.Setter;
import lombok.experimental.Accessors;
import ua.osb.quarkus.dailyman.todo.persistence.Audit;
import ua.osb.quarkus.dailyman.todo.persistence.TodoDao;
import ua.osb.quarkus.dailyman.todo.persistence.TodoEntity;

import javax.enterprise.inject.Vetoed;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Vetoed
public record TodoDaoStub(Map<Long, TodoEntity>entityById,
                          long generatesIdUponCreation,
                          List<TodoEntity>returnsAll,
                          ZonedDateTime timestampForUpdate)
        implements TodoDao {

    public static TodoDaoBuilder builder() {
        return new TodoDaoBuilder();
    }

    @Override
    public Optional<TodoEntity> findById(long id) {
        return Optional.ofNullable(entityById.get(id));
    }

    @Override
    public TodoEntity create(TodoEntity _new) {
        _new.setId(generatesIdUponCreation);
        _new.setAudit(new Audit());
        return _new;
    }

    @Override
    public TodoEntity update(TodoEntity updated) {
        updated.getAudit().setLastModifiedDate(timestampForUpdate);
        return updated;
    }

    @Override
    public List<TodoEntity> findAll() {
        return returnsAll;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Accessors(fluent = true)
    @Setter
    public static class TodoDaoBuilder {
        private Map<Long, TodoEntity> entityById;
        private long generatesIdUponCreation;
        private List<TodoEntity> returnsAll;
        private ZonedDateTime onUpdateInsertsTimestamp;

        public TodoDaoStub build() {
            return new TodoDaoStub(
                    entityById,
                    generatesIdUponCreation,
                    returnsAll,
                    onUpdateInsertsTimestamp
            );
        }
    }
}
