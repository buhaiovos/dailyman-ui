package ua.osb.quarkus.dailyman.todo.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Todo {
    private Long id;
    private String title;
    private String details;
    private ZonedDateTime createdDate;
    private ZonedDateTime lastModifiedDate;

    public static Todo with(Long id, String title, String details) {
        return new Todo(id, title, details, null, null);
    }

    public static Todo with(String title, String details) {
        return new Todo(null, title, details, null, null);
    }
}
