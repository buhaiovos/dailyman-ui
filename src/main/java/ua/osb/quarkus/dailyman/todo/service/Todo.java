package ua.osb.quarkus.dailyman.todo.service;

import java.time.ZonedDateTime;

public record Todo(
        Long id,
        String title,
        String details,
        ZonedDateTime createdDate,
        ZonedDateTime lastModifiedDate
) {
    public static Todo with(String title, String details) {
        return new Todo(
                null,
                title,
                details,
                null,
                null);
    }

    public static Todo with(Long id, String title, String details) {
        return new Todo(
                id,
                title,
                details,
                null,
                null);
    }

}
