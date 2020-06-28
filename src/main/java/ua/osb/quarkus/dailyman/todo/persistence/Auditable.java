package ua.osb.quarkus.dailyman.todo.persistence;

public interface Auditable {
    Audit getAudit();
    void setAudit(Audit audit);
}
