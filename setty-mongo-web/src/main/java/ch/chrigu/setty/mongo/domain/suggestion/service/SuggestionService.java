package ch.chrigu.setty.mongo.domain.suggestion.service;

import ch.chrigu.setty.mongo.domain.calendar.UserCalendarUpdatedEvent;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroupRepository;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroupUpdatedEvent;
import ch.chrigu.setty.mongo.domain.suggestion.CalendarEntry;
import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import ch.chrigu.setty.mongo.domain.suggestion.SuggestionFactory;
import ch.chrigu.setty.mongo.domain.suggestion.SuggestionRepository;
import ch.chrigu.setty.mongo.domain.user.User;
import ch.chrigu.setty.mongo.infrastructure.web.UriToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author christoph.huber
 * @since 31.01.2018
 */
@Service
@RequiredArgsConstructor
@RepositoryEventHandler
public class SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final SuggestionFactory suggestionFactory;
    private final MeetingGroupRepository meetingGroupRepository;
    private final UriToIdConverter uriToIdConverter;

    public Page<Suggestion> getOrCreateSuggestions(SuggestionCreateOptions options, Pageable pageable) {
        final String id = uriToIdConverter.convert(options.getMeetingGroup());
        final MeetingGroup meetingGroup = meetingGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting group with id " + id + " not found"));
        final List<Suggestion> newSuggestions = suggestionFactory
                .getNextSuggestions(meetingGroup, options.getNumOfWeeks());

        final List<Suggestion> result = new ArrayList<>(newSuggestions.size());
        newSuggestions.forEach(suggestion -> {
            final Suggestion add = suggestionRepository
                    .findByForGroupAndCalendarEntry(suggestion.getForGroup(), suggestion.getCalendarEntry())
                    .orElseGet(() -> suggestionRepository.insert(suggestion));
            result.add(add);
        });
        final int start = (int) pageable.getOffset();
        final int end = (start + pageable.getPageSize()) > result.size() ? result.size() : (start + pageable.getPageSize());
        final List<Suggestion> subList = result.subList(start, end);
        return new PageImpl<>(subList, pageable, result.size());
    }

    @HandleBeforeDelete
    public void meetingGroupDeleted(MeetingGroup meetingGroup) {
        List<Suggestion> suggestions = suggestionRepository.findByForGroup(meetingGroup);
        suggestionRepository.deleteAll(suggestions);
    }

    @EventListener
    public void userCalendarUpdated(UserCalendarUpdatedEvent event) {
        updateGroupsOf(event.getUserCalendar().getOwner());
    }

    @EventListener
    public void meetingGroupUpdatedEvent(MeetingGroupUpdatedEvent event) {
        updateGroup(event.getMeetingGroup());
    }

    private void updateGroupsOf(User user) {
        meetingGroupRepository.findAllByMembers(user).forEach(this::updateGroup);
    }

    private void updateGroup(MeetingGroup group) {
        List<Suggestion> calculatedSuggestions = suggestionFactory.getNextSuggestions(group, 4);
        List<Suggestion> oldSuggestions = suggestionRepository.findByForGroup(group);

        List<Suggestion> newSuggestions = getSuggestionsMinus(calculatedSuggestions, oldSuggestions);
        suggestionRepository.saveAll(newSuggestions);

        List<Suggestion> obsolete = getSuggestionsMinus(oldSuggestions, calculatedSuggestions);
        suggestionRepository.deleteAll(obsolete);
    }

    private List<Suggestion> getSuggestionsMinus(List<Suggestion> suggestions, List<Suggestion> subtractEqualCalendarEntries) {
        List<CalendarEntry> oldTimes = subtractEqualCalendarEntries.stream().map(Suggestion::getCalendarEntry).collect(Collectors.toList());
        return suggestions.stream().filter(s -> !oldTimes.contains(s.getCalendarEntry())).collect(Collectors.toList());
    }
}
