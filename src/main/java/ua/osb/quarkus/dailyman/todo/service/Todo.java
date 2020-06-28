package ua.osb.quarkus.dailyman.todo.service;

import java.time.ZonedDateTime;

public record Todo(
        Long id,
        String title,
        String details,
        ZonedDateTime createdDate,
        ZonedDateTime lastModifiedDate
) {}
