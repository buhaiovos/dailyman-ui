package ua.osb.quarkus.dailyman.todo.service;

import lombok.RequiredArgsConstructor;
import ua.osb.quarkus.dailyman.todo.persistence.TodoDao;
import ua.osb.quarkus.dailyman.todo.persistence.TodoEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@ApplicationScoped
class TodoServiceImpl implements TodoService {
    private final TodoDao dao;

    @Override
    public List<Todo> findAll() {
        List<TodoEntity> entities = dao.findAll();
        return entities.stream()
                .map(this::toServiceLevel)
                .collect(toList());
    }

    @Override
    public Todo create(Todo todo) {
        var newTodo = new TodoEntity();
        newTodo.setTitle(todo.title());
        newTodo.setDetails(todo.details());
        dao.create(newTodo);

        return toServiceLevel(newTodo);
    }

    @Override
    public Todo findById(long id) {
        return dao.findById(id)
                .map(this::toServiceLevel)
                .orElseThrow(() -> new ItemNotFound("No todo found with id " + id));
    }

    private Todo toServiceLevel(TodoEntity entity) {
        return new Todo(entity.getId(), entity.getTitle(),
                entity.getDetails(), entity.getAudit().getCreatedDate(),
                entity.getAudit().getLastModifiedDate());
    }
}
