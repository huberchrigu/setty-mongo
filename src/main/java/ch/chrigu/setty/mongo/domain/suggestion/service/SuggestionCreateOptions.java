package ch.chrigu.setty.mongo.domain.suggestion.service;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

/**
 * @author christoph.huber
 * @since 31.01.2018
 */
@Getter
@Setter
public class SuggestionCreateOptions {
    private int numOfWeeks = 4;

    @NotNull
    private URI forGroup;
}
