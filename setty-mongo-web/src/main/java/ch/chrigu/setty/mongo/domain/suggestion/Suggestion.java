package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.suggestion.reaction.UserReaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Suggestion extends AggregateRoot {
    @JsonProperty(required = true)
    @DBRef
    private MeetingGroup forGroup;

    private List<UserReaction> userReactions = new ArrayList<>();

    @JsonProperty(required = true)
    private CalendarEntry calendarEntry;

    Suggestion(MeetingGroup forGroup, CalendarEntry calendarEntry) {
        Assert.notNull(forGroup, "A suggestion must belong to a group");
        Assert.notNull(calendarEntry, "A suggestion needs a calendar entry");
        this.forGroup = forGroup;
        this.calendarEntry = calendarEntry;
    }
}