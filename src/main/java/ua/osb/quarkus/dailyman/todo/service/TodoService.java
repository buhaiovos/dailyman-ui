package ua.osb.quarkus.dailyman.todo.service;

import java.util.List;

public interface TodoService {
    Todo findById(long id);
    Todo create(Todo newTodo);
    List<Todo> findAll();
}
