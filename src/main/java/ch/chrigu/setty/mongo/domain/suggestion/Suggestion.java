package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import ch.chrigu.setty.mongo.domain.aggregate.WithCreatedBy;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.suggestion.reaction.ReactionType;
import ch.chrigu.setty.mongo.domain.suggestion.reaction.UserReaction;
import ch.chrigu.setty.mongo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Suggestion extends AggregateRoot implements WithCreatedBy {
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

    public void addVote(User user, ReactionType reactionType) {
        Assert.state(userReactions.stream().noneMatch(r -> r.getUser().equals(user)),
                "User " + user.getId() + " already voted");
        Assert.state(forGroup.getMembers().contains(user), "User " + user.getId() +
                " is not part of the group");
        userReactions.add(new UserReaction(user, reactionType));
    }

    /**
     * A suggestion belongs to the group's owner.
     */
    @JsonIgnore
    @Override
    public String getCreatedBy() {
        return forGroup.getCreatedBy();
    }
}
