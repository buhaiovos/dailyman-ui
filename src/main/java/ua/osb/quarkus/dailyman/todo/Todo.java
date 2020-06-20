package ua.osb.quarkus.dailyman.todo;

import java.time.ZonedDateTime;

public record Todo(
        long id,
        String title,
        String details,
        ZonedDateTime createdDate,
        ZonedDateTime lastModifiedDate
) {}
