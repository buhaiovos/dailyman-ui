package ua.osb.quarkus.dailyman.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import ua.osb.quarkus.dailyman.todo.TodoResource.TodoDto;
import ua.osb.quarkus.dailyman.todo.service.Todo;
import ua.osb.quarkus.dailyman.todo.service.TodoService;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
class TodoResourceTest {
    @InjectMock
    TodoService service;
    @Inject
    ObjectMapper objectMapper;

    @Test
    void getAll_contentTypeIsJson() {
        given()
                .when().get("/todos")
                .then()
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void getAll_whenThereAreTodos_returnsThemInArray() throws Exception {
        when(service.findAll()).thenReturn(List.of(withId(1), withId(2)));

        given()
                .when().get("/todos")
                .then()
                .body(is(objectMapper.writeValueAsString(List.of(dtoWithId(1), dtoWithId(2)))));

    }

    @Test
    void getAll_whenThereAreNoItems_returnsEmptyArray() {
        given()
                .when().get("/todos")
                .then()
                .statusCode(200).and().body("$", empty());
    }

    private Todo withId(long id) {
        return new Todo(id, null, null, null, null);
    }

    private TodoDto dtoWithId(long id) {
        return new TodoDto(id, null, null, null, null);
    }
}
