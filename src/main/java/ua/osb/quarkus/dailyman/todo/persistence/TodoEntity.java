package ua.osb.quarkus.dailyman.todo.persistence;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.osb.quarkus.dailyman.global.persistence.AuditListener;
import ua.osb.quarkus.dailyman.global.persistence.Auditable;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "todos")
@EntityListeners(AuditListener.class)
public class TodoEntity implements Auditable {
    // todo: wire up listener four Auditable
    //  fields update
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column
    private String details;
    @Embedded
    private Audit audit = new Audit();
}
