package ua.osb.quarkus.dailyman.global.persistence;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

public class AuditListener {
    @PrePersist
    void onPersist(Auditable aud) {
        aud.getAudit().setCreatedDate(ZonedDateTime.now());
    }

    @PreUpdate
    void onUpdate(Auditable aud) {
        aud.getAudit().setLastModifiedDate(ZonedDateTime.now());
    }
}
