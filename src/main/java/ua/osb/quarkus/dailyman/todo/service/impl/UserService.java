package ua.osb.quarkus.dailyman.todo.service.impl;

import lombok.RequiredArgsConstructor;
import ua.osb.quarkus.dailyman.todo.persistence.User;
import ua.osb.quarkus.dailyman.todo.persistence.UserRepository;

import javax.enterprise.context.ApplicationScoped;

@RequiredArgsConstructor
@ApplicationScoped
class UserService implements ua.osb.quarkus.dailyman.todo.service.UserService {
    private final UserRepository repo;

    @Override
    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public User createWithEmail(String email) {
        var newUser = new User(email);
        return repo.save(newUser);
    }
}
