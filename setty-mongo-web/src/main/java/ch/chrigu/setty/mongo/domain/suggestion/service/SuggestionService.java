package ch.chrigu.setty.mongo.domain.suggestion.service;

import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroupRepository;
import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import ch.chrigu.setty.mongo.domain.suggestion.SuggestionFactory;
import ch.chrigu.setty.mongo.domain.suggestion.SuggestionRepository;
import ch.chrigu.setty.mongo.infrastructure.web.UriToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author christoph.huber
 * @since 31.01.2018
 */
@Service
@RequiredArgsConstructor
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
}
