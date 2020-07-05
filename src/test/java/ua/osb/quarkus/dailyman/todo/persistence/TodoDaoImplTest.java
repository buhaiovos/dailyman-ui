package ua.osb.quarkus.dailyman.todo.persistence;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class TodoDaoImplTest {
    @Inject
    EntityManager entityManager;

    @Inject
    TodoDao subject;

    @Test
    void create_returnsPersistedObjectWithId() {
        var newTodo = new TodoEntity();
        newTodo.setTitle("Title");
        newTodo.setDetails("Change title");

        TodoEntity created = subject.create(newTodo);

        // returns object with id
        assertThat(created.getId()).isNotNull();
        // persisted object is discoverable via JPA
        var createdInDatabase = entityManager.find(TodoEntity.class, created.getId());
        assertThat(createdInDatabase.getTitle()).isEqualTo("Title");
        assertThat(createdInDatabase.getDetails()).isEqualTo("Change title");
    }

    @Test
    void findById_whenObjectIsPresentInJpaContext_returnNonEmptyOptional() {
        TodoEntity persistedTodo =
                whenSomeTodoIsPersisted("Do the cleaning", "Vacuum and wet");

        Optional<TodoEntity> saved = subject.findById(persistedTodo.getId());

        assertThat(saved).isNotEmpty();
        assertThat(saved).map(TodoEntity::getTitle).contains("Do the cleaning");
        assertThat(saved).map(TodoEntity::getDetails).contains("Vacuum and wet");
    }

    @Test
    void findById_whenObjectIsAbsentInJpaContext_returnEmptyOptional() {
        Optional<TodoEntity> optional = subject.findById(Integer.MAX_VALUE);

        assertThat(optional).isEmpty();
    }

    @Test
    void findAll_whenThereAreTwoObjectsPersistedIntoDatabase_returnListOfTwoEntities() {
        cleanUp();
        whenSomeTodoIsPersisted("First", "FD");
        whenSomeTodoIsPersisted("Second", "SD");

        List<TodoEntity> all = subject.findAll();

        assertThat(all).hasSize(2);
        assertThat(all).extracting(TodoEntity::getTitle).containsExactly("First", "Second");
        assertThat(all).extracting(TodoEntity::getDetails).containsExactly("FD", "SD");
    }

    @Test
    void update_whenExistingEntityProvided_persistsUpdatedToDatabase() {
        TodoEntity entity = whenSomeTodoIsPersisted("title before", "details before");
        entity.setTitle("title after");
        entity.setDetails("details after");

        subject.update(entity);

        TodoEntity persisted = entityManager.find(TodoEntity.class, entity.getId());
        assertThat(persisted.getTitle()).isEqualTo("title after");
        assertThat(persisted.getDetails()).isEqualTo("details after");
    }

    @Test
    void audit_whenEntityIsCreated_createdDateTimeIsInitialized() {
        var newTodo = new TodoEntity();
        newTodo.setTitle("Title");
        newTodo.setDetails("Change title");

        TodoEntity created = subject.create(newTodo);

        assertThat(created.getAudit().getCreatedDate()).isNotNull();
        assertThat(created.getAudit().getCreatedDate())
                .isEqualToIgnoringNanos(ZonedDateTime.now());
    }

    @Test
    void audit_whenEntityIsCreated_lastModifiedDateIsStillNull() {
        var newTodo = new TodoEntity();
        newTodo.setTitle("Title");
        newTodo.setDetails("Change title");

        TodoEntity created = subject.create(newTodo);

        assertThat(created.getAudit().getLastModifiedDate()).isNull();
        assertThat(created.getAudit().getCreatedDate()).isNotNull();
    }

    @Transactional
    public TodoEntity whenSomeTodoIsPersisted(String title, String details) {
        var persistedTodo = new TodoEntity();
        persistedTodo.setTitle(title);
        persistedTodo.setDetails(details);

        entityManager.persist(persistedTodo);

        return persistedTodo;
    }

    @Transactional
    public void cleanUp() {
        entityManager
                .createQuery("delete from TodoEntity")
                .executeUpdate();
    }
}
