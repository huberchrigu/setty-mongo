package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import ch.chrigu.setty.mongo.infrastructure.web.UriToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author christoph.huber
 * @since 01.02.2018
 */
@Component
@RequiredArgsConstructor
public class SuggestionResourceProcessor implements ResourceProcessor<Resource<Suggestion>> {
    private final UriToIdConverter uriToIdConverter;

    @Override
    public Resource<Suggestion> process(Resource<Suggestion> suggestionResource) {
        final String id;
        try {
            id = uriToIdConverter.convert(new URI(suggestionResource.getId().getHref()));
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
        suggestionResource.add(
                new Link(
                        linkTo(methodOn(SuggestionController.class).vote(id, null)).toString(),
                        "vote")
        );
        return suggestionResource;
    }
}
