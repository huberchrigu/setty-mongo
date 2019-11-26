package ch.chrigu.setty.mongo.domain.aggregate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;

import java.util.Date;

@Getter
@EqualsAndHashCode
public abstract class AggregateRoot {
    @Id
    private String id;

    @Version
    private Long version;

    @ReadOnlyProperty
    @LastModifiedDate
    private Date lastModified;
}