package ua.osb.quarkus.dailyman.todo;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class TodoService {
    public List<Todo> getAll() {
        return Collections.emptyList();
    }
}
