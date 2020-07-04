package ua.osb.quarkus.dailyman.global.persistence;

import ua.osb.quarkus.dailyman.todo.persistence.Audit;

public interface Auditable {
    Audit getAudit();
    void setAudit(Audit audit);
}
