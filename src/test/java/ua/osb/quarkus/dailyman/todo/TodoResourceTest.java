package ua.osb.quarkus.dailyman.todo;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class TodoResourceTest {
//    @InjectMock
//    TodoService service;
//    @Inject
//    ObjectMapper objectMapper;
//
//    @BeforeAll
//    static void setUp() {
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//    }
//
//    @Test
//    void getAll_contentTypeIsJson() {
//        given().get("/todos")
//                .then()
//                .contentType(MediaType.APPLICATION_JSON);
//    }
//
//    @Test
//    void getAll_whenThereAreTodos_returnsThemInArray() throws Exception {
//        when(service.findAll()).thenReturn(List.of(withId(1), withId(2)));
//
//        given().get("/todos")
//                .then()
//                .body(is(objectMapper.writeValueAsString(List.of(dtoWithId(1), dtoWithId(2)))));
//
//    }
//
//    @Test
//    void getAll_whenThereAreNoItems_returnsEmptyArray() {
//        given()
//                .when().get("/todos")
//                .then()
//                .statusCode(200).and().body("$", empty());
//    }
//
//    @Test
//    void create_whenValidDtoIsPosted_createsNewTodoAndReturnsItWithHttp201() {
//        var now = ZonedDateTime.now();
//        when(service.create(any(Todo.class)))
//                .thenReturn(new Todo(42L, "created title", "created details", now, now));
//
//        given().body(new TodoDto(null, "new title", "new details", null, null))
//                .and()
//                .contentType(ContentType.JSON)
//                .when().post("/todos")
//                .then()
//                .statusCode(201)
//                .and()
//                .body("id", is(42),
//                        "title", is("created title"),
//                        "details", is("created details"),
//                        "created_date", not(empty()),
//                        "last_modified_date", not(empty()));
//    }
//
//    @Test
//    void create_whenTodoDoesNotHaveTitle_returnsHttpStatusAndMessage() {
//        when(service.create(any())).thenReturn(Todo.with("foo", "bar"));
//
//        given().body(new TodoCreationRequest(null, "new details"))
//                .and()
//                .contentType(ContentType.JSON)
//                .when().post("/todos")
//                .then()
//                .statusCode(400)
//                .and()
//                .body("parameterViolations.message", contains("Title should not be blank"));
//    }
//
//    @Test
//    void update_whenValidRequest_returnUpdated() {
//        doAnswer(returnsFirstArg()).when(service).update(any());
//
//        given().body(new TodoUpdateRequest("updated title", "updated details"))
//                .and()
//                .contentType(ContentType.JSON)
//                .when().put("/todos/1")
//                .then()
//                .statusCode(200)
//                .and()
//                .body("id", is(1),
//                        "title", is("updated title"),
//                        "details", is("updated details"));
//    }
//
//    private Todo withId(long id) {
//        return new Todo(id, null, null, null, null);
//    }
//
//    private TodoDto dtoWithId(long id) {
//        return new TodoDto(id, null, null, null, null);
//    }
}
