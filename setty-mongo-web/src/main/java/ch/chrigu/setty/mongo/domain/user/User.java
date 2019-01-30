package ch.chrigu.setty.mongo.domain.user;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class User extends AggregateRoot {

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