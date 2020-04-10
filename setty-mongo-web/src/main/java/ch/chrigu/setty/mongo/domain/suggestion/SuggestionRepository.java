package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Security: Where a {@link RestResource} is not exposed, security checks should be moved to the service layer.
 *
 * @author christoph.huber
 * @since 29.01.2018
 */
@PreAuthorize("hasRole('ADMIN')")
public interface SuggestionRepository extends MongoRepository<Suggestion, String> {

    @PreAuthorize("isAuthenticated()")
    @RestResource(exported = false)
    @Override
    @Nullable
    <S extends Suggestion> S save(@Nullable S suggestion);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.areAllCreatedByCurrentUser(#suggestions)")
    @Override
    @Nullable
    <S extends Suggestion> List<S> saveAll(@Nullable Iterable<S> suggestions);

    @RestResource(exported = false)
    Optional<Suggestion> findByForGroupAndCalendarEntry(MeetingGroup forGroup, CalendarEntry calendarEntry);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isNonNullAndRelatedToCurrentUser(#forGroup)")
    List<Suggestion> findByForGroup(MeetingGroup forGroup);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.areAllRelatedToCurrentUser(#groups)")
    List<Suggestion> findByForGroupIn(List<MeetingGroup> groups);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isNonNullAndRelatedToCurrentUser(#forGroup)")
    List<Suggestion> findByForGroupAndCalendarEntryFromAfterAndCalendarEntryToBefore(MeetingGroup forGroup, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
                                                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to);

    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') || @currentUserService.isNonNullAndVisible(returnObject.orElse(null))")
    @Override
    Optional<Suggestion> findById(String id);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCreatedByCurrentUser(#suggestion)")
    @Override
    void delete(Suggestion suggestion);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.areAllCreatedByCurrentUser(#suggestions)")
    @Override
    void deleteAll(Iterable<? extends Suggestion> suggestions);
}
