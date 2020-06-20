package ua.osb.quarkus.dailyman.todo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
    private final TodoService service;

    @GET
    public List<TodoDto> getAll() {
        List<Todo> todos = service.getAll();
        return todos.stream()
                .map(this::toDto)
                .collect(toList());
    }

    private TodoDto toDto(Todo todo) {
        return new TodoDto(
                todo.id(),
                todo.title(),
                todo.details(),
                todo.createdDate(),
                todo.lastModifiedDate()
        );
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class TodoDto {
        private long id;
        private String title;
        private String details;
        private ZonedDateTime createdDate;
        private ZonedDateTime lastModifiedDate;
    }
}
