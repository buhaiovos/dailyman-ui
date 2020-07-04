package ua.osb.quarkus.dailyman.todo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ua.osb.quarkus.dailyman.todo.service.Todo;
import ua.osb.quarkus.dailyman.todo.service.TodoService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@Path("todos")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class TodoResource {
    private final TodoService service;

    @GET
    public List<TodoDto> getAll() {
        List<Todo> todos = service.findAll();
        return todos.stream()
                .map(this::toDto)
                .collect(toList());
    }

    @POST
    public Response create(@Valid TodoCreationRequest newTodo) {
        var _new = Todo.with(newTodo.title, newTodo.details);
        var created = service.create(_new);
        var createdDto = toDto(created);

        return Response
                .status(Response.Status.CREATED)
                .entity(createdDto)
                .build();
    }

    @PUT
    @Path("/{id}")
    public TodoDto update(@PathParam("id") Long id, @Valid TodoUpdateRequest updatedDto) {
        var toBeUpdated = Todo.with(id, updatedDto.title, updatedDto.details);
        var updated = service.update(toBeUpdated);
        return toDto(updated);
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
        private Long id;
        private String title;
        private String details;
        private ZonedDateTime createdDate;
        private ZonedDateTime lastModifiedDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TodoCreationRequest {
        @NotBlank(message = "Title should not be blank")
        private String title;
        private String details;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TodoUpdateRequest {
        private String title;
        private String details;
    }
}
