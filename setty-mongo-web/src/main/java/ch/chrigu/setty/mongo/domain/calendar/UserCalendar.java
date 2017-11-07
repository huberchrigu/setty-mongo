package ch.chrigu.setty.mongo.domain.calendar;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import ch.chrigu.setty.mongo.domain.declaration.RequiredForDeserialization;
import ch.chrigu.setty.mongo.domain.suggestion.CalendarEntry;
import ch.chrigu.setty.mongo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

import java.util.Set;

@Getter
public class UserCalendar extends AggregateRoot {

    @JsonProperty(required = true)
    @DBRef
    private User owner;

    private Set<CalendarEntry> entries;

    public UserCalendar(User owner, Set<CalendarEntry> entries) {
        Assert.notNull(owner, "A user calendar requires an owner");
        this.owner = owner;
        this.entries = entries;
    }


    /**
     * Required for setting the "owner" property by URI String. This does not work with constructors.
     * Therefore this also bypasses the domain validation in the "normal" constructor.
     */
    @RequiredForDeserialization
    protected UserCalendar() {
    }
}