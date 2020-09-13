package ua.osb.quarkus.dailyman.todo.service;

import ua.osb.quarkus.dailyman.todo.persistence.User;

public interface UserService {
    User findByEmail(String email);
    User createWithEmail(String email);
}
