package ch.chrigu.setty.mongo.domain.user;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import ch.chrigu.setty.mongo.domain.aggregate.WithCreatedBy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.util.Assert;

@Getter
public class User extends AggregateRoot implements WithCreatedBy {
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @JsonProperty(required = true)
    private String lastName;

    @JsonProperty(required = true)
    private String firstName;

    public User(String lastName, String firstName) {
        Assert.state(!lastName.isEmpty(), "A user needs a last name");
        Assert.state(!firstName.isEmpty(), "A user needs a first name");
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
