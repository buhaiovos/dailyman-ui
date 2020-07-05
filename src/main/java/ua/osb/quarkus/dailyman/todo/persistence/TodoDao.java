package ua.osb.quarkus.dailyman.todo.persistence;

import java.util.List;
import java.util.Optional;

public interface TodoDao {
    Optional<TodoEntity> findById(long id);

    TodoEntity create(TodoEntity _new);

    TodoEntity update(TodoEntity updated);

    List<TodoEntity> findAll();
}
