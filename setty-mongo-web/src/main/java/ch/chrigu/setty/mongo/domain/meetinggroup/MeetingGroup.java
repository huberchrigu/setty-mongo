package ch.chrigu.setty.mongo.domain.meetinggroup;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import ch.chrigu.setty.mongo.domain.meetinggroup.preference.MeetingPreference;
import ch.chrigu.setty.mongo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
public class MeetingGroup extends AggregateRoot {
    @DBRef
    private Set<User> members;

    private List<MeetingPreference> preferences;

    @JsonProperty(required = true)
    private String name;
}