package ch.chrigu.setty.mongo.domain.meetinggroup.web;

import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author christoph.huber
 */
@Component
public class MeetingGroupSearchLinkProcessor implements RepresentationModelProcessor<RepositorySearchesResource> {
    @Override
    public RepositorySearchesResource process(RepositorySearchesResource suggestionRepositorySearchesResource) {
        if (suggestionRepositorySearchesResource.getDomainType().equals(MeetingGroup.class)) {
            suggestionRepositorySearchesResource.add(
                    new Link(linkTo(methodOn(MeetingGroupController.class)
                            .findByMembersCreatedBy(null)).toString(),
                            "findByMembersCreatedBy")
            );
        }
        return suggestionRepositorySearchesResource;
    }
}
