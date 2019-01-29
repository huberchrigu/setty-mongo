package ch.chrigu.setty.mongo.domain.aggregate;

import lombok.Getter;
import org.springframework.data.annotation.*;
import org.springframework.hateoas.Identifiable;

import java.util.Date;

@Getter
public class AggregateRoot implements Identifiable<String> {
    @Id
    private String id;

    @Version
    private Long version;

    @ReadOnlyProperty
    @LastModifiedDate
    private Date lastModified;
}