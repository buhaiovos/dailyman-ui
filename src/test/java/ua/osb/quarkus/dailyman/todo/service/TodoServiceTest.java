package ua.osb.quarkus.dailyman.todo.service;

import org.junit.jupiter.api.Test;
import ua.osb.quarkus.dailyman.todo.TodoDaoStub;
import ua.osb.quarkus.dailyman.todo.persistence.Audit;
import ua.osb.quarkus.dailyman.todo.persistence.TodoEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TodoServiceTest {
    TodoEntity testEntity = createEntity("todo", "do stuff", 21L);
    TodoEntity firstOfAll = createEntity("first", "first things first", 22L);
    TodoEntity secondOfAll = createEntity("second", "then second", 23L);

    @Test
    void getAll_whenRepoReturnsTwoEntities_returnTwoServiceModels() {
        var daoStub = TodoDaoStub.builder()
                .returnsAll(List.of(firstOfAll, secondOfAll))
                .build();
        var subject = new TodoServiceImpl(daoStub);

        List<Todo> actual = subject.findAll();

        assertThat(actual).hasSize(2);
        assertThat(actual)
                .extracting(Todo::id)
                .containsExactly(22L, 23L);
        assertThat(actual)
                .extracting(Todo::title)
                .containsExactly("first", "second");
        assertThat(actual)
                .extracting(Todo::details)
                .containsExactly("first things first", "then second");
    }

    @Test
    void create_returnsPersistedObjectConvertedToServiceModel() {
        var daoStub = TodoDaoStub.builder()
                .generatesIdUponCreation(42L)
                .build();
        var subject = new TodoServiceImpl(daoStub);

        Todo created = subject.create(new Todo(0L, "title", "details", null, null));

        assertThat(created.id()).isEqualTo(42L);
        assertThat(created.title()).isEqualTo("title");
        assertThat(created.details()).isEqualTo("details");
    }

    @Test
    void getById_whenEntityPresent_returnsFoundEntityConvertedToServiceModel() {
        var daoStub = TodoDaoStub.builder()
                .entityById(Map.of(21L, testEntity))
                .build();
        var subject = new TodoServiceImpl(daoStub);

        Todo actual = subject.findById(21L);

        assertThat(actual.title()).isEqualTo(testEntity.getTitle());
        assertThat(actual.details()).isEqualTo(testEntity.getDetails());
        assertThat(actual.id()).isEqualTo(testEntity.getId());
    }

    @Test
    void getById_whenNoEntity_throwNotFoundException() {
        var daoStub = TodoDaoStub.builder()
                .entityById(emptyMap())
                .build();

        var subject = new TodoServiceImpl(daoStub);

        assertThrows(ItemNotFound.class, () -> subject.findById(0), "No todo found with id 0");
    }

    @Test
    void update_whenEntityIsPresent_updatesAndReturnsUpdated() {
        ZonedDateTime updateTimestamp =
                ZonedDateTime.of(LocalDateTime.of(2000, 1, 1, 10, 11, 12), ZoneId.of("UTC"));
        var daoStub = TodoDaoStub.builder()
                .entityById(Map.of(21L, testEntity))
                .onUpdateInsertsTimestamp(updateTimestamp)
                .build();

        var subject = new TodoServiceImpl(daoStub);

        Todo updated = subject.update(
                new Todo(21L, "updated title", "updated details", null, null)
        );

        assertThat(updated.id()).isEqualTo(21L);
        assertThat(updated.title()).isEqualTo("updated title");
        assertThat(updated.details()).isEqualTo("updated details");
        assertThat(updated.lastModifiedDate()).isEqualTo(updateTimestamp);
    }

    @Test
    void update_whenEntityIsNotPresent_throwsItemNotFound() {
        var daoStub = TodoDaoStub.builder()
                .entityById(emptyMap())
                .build();

        var subject = new TodoServiceImpl(daoStub);

        assertThrows(ItemNotFound.class,
                () -> subject.update(new Todo(1L, null, null, null, null)),
                "No todo found with id 1");
    }

    @Test
    void update_whenIdOfRequestedUpdateIsNull_throwsNullPointerException() {
        var daoStub = TodoDaoStub.builder().build();

        var subject = new TodoServiceImpl(daoStub);

        assertThrows(NullPointerException.class,
                () -> subject.update(new Todo(null, null, null, null, null)),
                "Id for update is not provided");
    }

    private TodoEntity createEntity(String title, String details, long id) {
        TodoEntity entity = new TodoEntity();
        entity.setTitle(title);
        entity.setDetails(details);
        entity.setId(id);
        entity.setAudit(new Audit());

        return entity;
    }
}
