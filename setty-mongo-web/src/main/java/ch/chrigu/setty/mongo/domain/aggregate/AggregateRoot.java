package ch.chrigu.setty.mongo.domain.aggregate;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import lombok.Getter;

@Getter
public class AggregateRoot {
    @Id
    private String id;

    @Version
    private Long version;

    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date lastModified;
}