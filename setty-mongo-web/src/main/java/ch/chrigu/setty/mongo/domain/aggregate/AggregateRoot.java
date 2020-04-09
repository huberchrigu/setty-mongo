package ch.chrigu.setty.mongo.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.util.Date;

@Getter
@EqualsAndHashCode
public abstract class AggregateRoot implements WithId {
    @Id
    private String id;

    @Version
    private Long version;

    @LastModifiedDate
    @JsonIgnore
    private Date lastModified;
}
