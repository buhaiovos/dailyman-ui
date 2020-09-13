package ua.osb.quarkus.dailyman.todo.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/todos")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RegisterRestClient(configKey="todo-api")
public interface TodoClient {
    @GET
    @Path("/{id}")
    Todo findById(@PathParam long id);

    @POST
    Todo create(Todo newTodo);

    @PUT
    @Path("/{id}")
    Todo update(@PathParam long id, Todo updatedTodo);

    @GET
    List<Todo> findAll();
}
