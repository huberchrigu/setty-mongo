package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

/**
 * @author christoph.huber
 * @since 02.02.2018
 */
@AllArgsConstructor
@Component
public class SuggestionResourceAssembler implements ResourceAssembler<Suggestion, Resource<Suggestion>> {
    private final EntityLinks entityLinks;

    @Override
    public Resource<Suggestion> toResource(Suggestion entity) {
        return new Resource<>(entity,
                entityLinks.linkToSingleResource(entity).withSelfRel(),
                entityLinks.linkToSingleResource(entity),
                entityLinks.linkToSingleResource(entity.getForGroup()).withRel("forGroup")); // TODO: Should be .../forGroup
    }
}
