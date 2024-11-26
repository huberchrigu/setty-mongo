package ch.chrigu.setty.mongo.domain.suggestion.service;

import ch.chrigu.setty.mongo.domain.suggestion.reaction.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

@AllArgsConstructor
@Getter
public class Vote {
    @NotNull
    private final URI user;
    @NotNull
    private final ReactionType reactionType;
}
