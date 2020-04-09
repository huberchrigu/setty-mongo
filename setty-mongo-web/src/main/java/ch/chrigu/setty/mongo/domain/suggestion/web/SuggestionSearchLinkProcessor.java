package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author christoph.huber
 * @since 01.02.2018
 */
@Component
public class SuggestionSearchLinkProcessor implements RepresentationModelProcessor<RepositorySearchesResource> {
    @Override
    public RepositorySearchesResource process(RepositorySearchesResource suggestionRepositorySearchesResource) {
        if (suggestionRepositorySearchesResource.getDomainType().equals(Suggestion.class)) {
            suggestionRepositorySearchesResource.add(
                    new Link(linkTo(methodOn(SuggestionController.class)
                            .findNext(null, null)).toString() + "{?forGroup,numOfWeeks}",
                            "getNext"),
                    new Link(linkTo(methodOn(SuggestionController.class)
                            .findByForGroupMembersCreatedBy(null)).toString(),
                            "getNext")
            );
        }
        return suggestionRepositorySearchesResource;
    }
}
