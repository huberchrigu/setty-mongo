package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.suggestion.reaction.UserReaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Getter
class Suggestion extends AggregateRoot {
    @JsonProperty(required = true)
    @DBRef
    private MeetingGroup forGroup;

    private List<UserReaction> userReactions = new ArrayList<>();

    @JsonProperty(required = true)
    private CalendarEntry calendarEntry;

    Suggestion(MeetingGroup forGroup, CalendarEntry calendarEntry) {
        this.forGroup = forGroup;
        this.calendarEntry = calendarEntry;
    }
}