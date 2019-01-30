package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * @author christoph.huber
 * @since 29.01.2018
 */
public interface SuggestionRepository extends MongoRepository<Suggestion, String> {
    @RestResource(exported = false)
    @Override
    @Nullable
    <S extends Suggestion> S save(@Nullable S suggestion);

    @RestResource(exported = false)
    @Override
    @Nullable
    <S extends Suggestion> List<S> saveAll(@Nullable Iterable<S> suggestions);

    List<Suggestion> findByForGroup(MeetingGroup forGroup);

    Optional<Suggestion> findByForGroupAndCalendarEntry(MeetingGroup forGroup, CalendarEntry calendarEntry);
}
