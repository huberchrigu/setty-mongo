package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * @author christoph.huber
 * @since 02.02.2018
 */
@Component
@RequiredArgsConstructor
public class SuggestionModelAssembler implements RepresentationModelAssembler<Suggestion, EntityModel<Suggestion>> {
    private final RepositoryEntityLinks entityLinks;

    @Override
    public EntityModel<Suggestion> toModel(Suggestion entity) {
        return new EntityModel<>(entity,
                entityLinks.linkToItemResource(entity, Suggestion::getId).withSelfRel(),
                entityLinks.linkToItemResource(entity, Suggestion::getId),
                entityLinks.linkToItemResource(entity.getForGroup(), MeetingGroup::getId).withRel("forGroup")
        );
    }
}
