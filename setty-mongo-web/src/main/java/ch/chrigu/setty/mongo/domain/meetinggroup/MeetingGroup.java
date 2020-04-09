package ch.chrigu.setty.mongo.domain.meetinggroup;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import ch.chrigu.setty.mongo.domain.aggregate.WithCreatedBy;
import ch.chrigu.setty.mongo.domain.declaration.RequiredForDeserialization;
import ch.chrigu.setty.mongo.domain.meetinggroup.preference.MeetingPreference;
import ch.chrigu.setty.mongo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

@Getter
public class MeetingGroup extends AggregateRoot implements WithCreatedBy {
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @DBRef
    private Set<User> members;

    private List<MeetingPreference> preferences;

    @JsonProperty(required = true)
    private String name;

    /**
     * The lombok constructor with <code>@NonNull</code> does not work.
     */
    public MeetingGroup(Set<User> members, List<MeetingPreference> preferences, String name) {
        Assert.state(!name.isEmpty(), "A meeting group must have a name");
        this.members = members;
        this.preferences = preferences;
        this.name = name;
    }

    /**
     * The no-args constructor is required for linking members. The normal constructor (with preconditions) does not work
     * yet.
     */
    @RequiredForDeserialization
    protected MeetingGroup() {
    }

    @DomainEvents
    public MeetingGroupUpdatedEvent sendEvent() {
        return new MeetingGroupUpdatedEvent(this);
    }
}
