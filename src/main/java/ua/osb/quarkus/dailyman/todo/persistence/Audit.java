package ua.osb.quarkus.dailyman.todo.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.ZonedDateTime;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    @Column(name = "created_date")
    private ZonedDateTime createdDate;
    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;
}
