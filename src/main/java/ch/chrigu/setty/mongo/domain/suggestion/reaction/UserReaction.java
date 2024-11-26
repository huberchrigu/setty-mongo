package ch.chrigu.setty.mongo.domain.suggestion.reaction;

import ch.chrigu.setty.mongo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@AllArgsConstructor
public class UserReaction {
    @JsonProperty(required = true)
    @DBRef
    private User user;

    @JsonProperty(required = true)
    private ReactionType type;
}
