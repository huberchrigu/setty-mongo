package ch.chrigu.setty.mongo.domain.calendar.web;

import ch.chrigu.setty.mongo.domain.calendar.CustomUserCalendarRepository;
import ch.chrigu.setty.mongo.domain.calendar.UserCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestController
@RequestMapping("/userCalendars")
@RequiredArgsConstructor
public class UserCalendarController {
    private final CustomUserCalendarRepository userCalendarRepository;
    private final UserCalendarAssembler userCalendarAssembler;

    @GetMapping("/search/findByOwnerCreatedBy")
    public ResponseEntity<CollectionModel<EntityModel<UserCalendar>>> findByOwnerCreatedBy(@RequestParam String createdBy) {
        List<UserCalendar> result = userCalendarRepository.findByOwnerCreatedBy(createdBy);
        return ResponseEntity.ok(userCalendarAssembler.toCollectionModel(result));
    }
}
