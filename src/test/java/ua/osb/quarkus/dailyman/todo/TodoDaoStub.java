package ua.osb.quarkus.dailyman.todo;

import lombok.Setter;
import lombok.experimental.Accessors;
import ua.osb.quarkus.dailyman.todo.persistence.Audit;
import ua.osb.quarkus.dailyman.todo.persistence.TodoDao;
import ua.osb.quarkus.dailyman.todo.persistence.TodoEntity;

import javax.enterprise.inject.Vetoed;
import java.util.List;
import java.util.Optional;

@Vetoed
public record TodoDaoStub
        (Optional<TodoEntity>returnsById, long generatesIdUponCreation, List<TodoEntity>returnsAll)
        implements TodoDao {

    public static TodoDaoBuilder builder() {
        return new TodoDaoBuilder();
    }

    @Override
    public Optional<TodoEntity> findById(long id) {
        return returnsById;
    }

    @Override
    public TodoEntity create(TodoEntity _new) {
        _new.setId(generatesIdUponCreation);
        _new.setAudit(new Audit());
        return _new;
    }

    @Override
    public List<TodoEntity> findAll() {
        return returnsAll;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Accessors(fluent = true)
    @Setter
    public static class TodoDaoBuilder {
        private Optional<TodoEntity> returnsById;
        private long generatesIdUponCreation;
        private List<TodoEntity> returnsAll;

        public TodoDaoStub build() {
            return new TodoDaoStub(returnsById, generatesIdUponCreation, returnsAll);
        }
    }
}
