package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author christoph.huber
 * @since 01.02.2018
 */
@Component
public class SuggestionSearchLinkProcessor implements ResourceProcessor<RepositorySearchesResource> {
    @Override
    public RepositorySearchesResource process(RepositorySearchesResource suggestionRepositorySearchesResource) {
        if (suggestionRepositorySearchesResource.getDomainType().equals(Suggestion.class)) {
            suggestionRepositorySearchesResource.add(new Link(
                    linkTo(methodOn(SuggestionController.class)
                            .findNext(null, null))
                            .toString() + "{?meetingGroup,numOfWeeks}",
                    "getNext"));
        }
        return suggestionRepositorySearchesResource;
    }
}
