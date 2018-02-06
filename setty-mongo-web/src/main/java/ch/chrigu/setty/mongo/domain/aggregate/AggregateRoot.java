package ch.chrigu.setty.mongo.domain.aggregate;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.hateoas.Identifiable;

import java.util.Date;

@Getter
public class AggregateRoot implements Identifiable<String> {
    @Id
    private String id;

    @Version
    private Long version;

    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date lastModified;
}