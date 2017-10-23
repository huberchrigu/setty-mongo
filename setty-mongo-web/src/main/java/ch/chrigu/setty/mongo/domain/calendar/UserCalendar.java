package ch.chrigu.setty.mongo.domain.calendar;

import java.util.Set;

import ch.chrigu.setty.mongo.domain.suggestion.CalendarEntry;
import ch.chrigu.setty.mongo.domain.declaration.RequiredForDeserialization;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import ch.chrigu.setty.mongo.domain.user.User;
import lombok.Getter;

/**
 * Due to a jackson bug (https://github.com/FasterXML/jackson-databind/issues/1722), do not use Lombok's @AllArgsConstructor
 * here.
 */
@Getter
public class UserCalendar extends AggregateRoot {

    @JsonProperty(required = true)
    @DBRef
    private User owner;

    private Set<CalendarEntry> entries;

    public UserCalendar(User owner, Set<CalendarEntry> entries) {
        this.owner = owner;
        this.entries = entries;
    }

    @RequiredForDeserialization
    UserCalendar() {
    }
}