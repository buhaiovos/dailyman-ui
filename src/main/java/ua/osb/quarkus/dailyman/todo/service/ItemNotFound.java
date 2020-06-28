package ua.osb.quarkus.dailyman.todo.service;

public class ItemNotFound extends RuntimeException {
    public ItemNotFound(String message) {
        super(message);
    }
}
