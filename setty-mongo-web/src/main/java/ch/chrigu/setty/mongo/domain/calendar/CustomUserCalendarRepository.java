package ch.chrigu.setty.mongo.domain.calendar;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CustomUserCalendarRepository {

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCurrentUser(#createdBy)")
    List<UserCalendar> findByOwnerCreatedBy(String createdBy);
}
