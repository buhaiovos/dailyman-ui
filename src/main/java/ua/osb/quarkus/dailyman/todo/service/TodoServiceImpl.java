package ua.osb.quarkus.dailyman.todo.service;

import lombok.RequiredArgsConstructor;
import ua.osb.quarkus.dailyman.todo.persistence.TodoDao;
import ua.osb.quarkus.dailyman.todo.persistence.TodoEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@ApplicationScoped
class TodoServiceImpl implements TodoService {
    private final TodoDao dao;

    @Override
    public List<Todo> findAll() {
        List<TodoEntity> entities = dao.findAll();
        return entities.stream()
                .map(this::mappedToServiceLevel)
                .collect(toList());
    }

    @Override
    public Todo create(Todo todo) {
        var newTodo = new TodoEntity();
        newTodo.setTitle(todo.title());
        newTodo.setDetails(todo.details());

        return mappedToServiceLevel(dao.create(newTodo));
    }

    @Override
    public Todo update(Todo updatedTodo) {
        TodoEntity todoEntity = getEntity(updatedTodo.id());
        todoEntity.setTitle(updatedTodo.title());
        todoEntity.setDetails(updatedTodo.details());

        return mappedToServiceLevel(dao.update(todoEntity));
    }

    private TodoEntity getEntity(Long idOrNull) {
        long id = requireNonNull(idOrNull, "Id for update is not provided");
        return dao.findById(id)
                .orElseThrow(() -> itemNotFound(id));
    }

    @Override
    public Todo findById(long id) {
        return dao.findById(id)
                .map(this::mappedToServiceLevel)
                .orElseThrow(() -> itemNotFound(id));
    }

    private ItemNotFound itemNotFound(long id) {
        return new ItemNotFound("No todo found with id " + id);
    }

    private Todo mappedToServiceLevel(TodoEntity entity) {
        return new Todo(entity.getId(), entity.getTitle(),
                entity.getDetails(), entity.getAudit().getCreatedDate(),
                entity.getAudit().getLastModifiedDate());
    }
}
