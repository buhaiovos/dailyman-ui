package ua.osb.quarkus.dailyman.todo.web;

import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ua.osb.quarkus.dailyman.todo.client.Todo;
import ua.osb.quarkus.dailyman.todo.client.TodoClient;
import ua.osb.quarkus.dailyman.todo.client.TodoCreationRequest;
import ua.osb.quarkus.dailyman.todo.client.TodoUpdateRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/todos")
@Authenticated
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class TodoResource {

    @Inject
    @RestClient
    TodoClient client;

    @GET
    public List<Todo> getAll() {
        return client.findAll();
    }

    @POST
    public Todo create(@Valid TodoCreationRequest newTodo) {
        var _new = Todo.with(newTodo.title(), newTodo.details());
        return client.create(_new);
    }

    @PUT
    @Path("/{id}")
    public Todo update(@PathParam("id") Long id, @Valid TodoUpdateRequest updatedDto) {
        var toBeUpdated = Todo.with(id, updatedDto.title(), updatedDto.details());
        return client.update(toBeUpdated.getId(), toBeUpdated);
    }
}
