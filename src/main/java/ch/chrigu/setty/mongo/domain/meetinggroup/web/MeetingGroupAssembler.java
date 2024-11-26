package ch.chrigu.setty.mongo.domain.meetinggroup.web;

import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingGroupAssembler implements RepresentationModelAssembler<MeetingGroup, EntityModel<MeetingGroup>> {
    private final RepositoryEntityLinks entityLinks;

    @Override
    public EntityModel<MeetingGroup> toModel(MeetingGroup entity) {
        return EntityModel.of(entity,
                entityLinks.linkToItemResource(entity, MeetingGroup::getId).withSelfRel(),
                entityLinks.linkToItemResource(entity, MeetingGroup::getId),
                entityLinks.linkFor(User.class).slash("meetingGroups").slash(entity.getId()).slash("members").withRel("members"));
    }
}
