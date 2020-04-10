package ch.chrigu.setty.mongo.domain.calendar.web;

import ch.chrigu.setty.mongo.domain.calendar.UserCalendar;
import ch.chrigu.setty.mongo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCalendarAssembler implements RepresentationModelAssembler<UserCalendar, EntityModel<UserCalendar>> {
    private final RepositoryEntityLinks entityLinks;

    @Override
    public EntityModel<UserCalendar> toModel(UserCalendar entity) {
        return new EntityModel<>(entity,
                entityLinks.linkToItemResource(entity, UserCalendar::getId).withSelfRel(),
                entityLinks.linkToItemResource(entity, UserCalendar::getId),
                entityLinks.linkToItemResource(entity.getOwner(), User::getId).withRel("owner"));
    }
}
