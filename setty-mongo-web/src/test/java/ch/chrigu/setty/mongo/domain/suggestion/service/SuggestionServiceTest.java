package ch.chrigu.setty.mongo.domain.suggestion.service;

import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroupRepository;
import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import ch.chrigu.setty.mongo.domain.suggestion.SuggestionFactory;
import ch.chrigu.setty.mongo.domain.suggestion.SuggestionRepository;
import ch.chrigu.setty.mongo.infrastructure.web.UriToIdConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author christoph.huber
 * @since 31.01.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class SuggestionServiceTest {
    @Mock
    private SuggestionFactory suggestionFactory;
    @Mock
    private SuggestionRepository suggestionRepository;
    @Mock
    private MeetingGroupRepository meetingGroupRepository;
    @Mock
    private UriToIdConverter uriToIdConverter;

    @Mock
    private MeetingGroup meetingGroup;
    @Mock
    private Suggestion suggestion;

    @Mock
    private SuggestionCreateOptions options;
    @Mock
    private Pageable pageable;

    @InjectMocks
    private SuggestionService testee;

    @Test
    public void shouldCreateASuggestionOnlyOnce() {
        final URI uri = URI.create("http://localhost:8080/resources/123");
        when(options.getMeetingGroup()).thenReturn(uri);
        when(options.getNumOfWeeks()).thenReturn(4);
        when(pageable.getPageSize()).thenReturn(5);

        when(uriToIdConverter.convert(eq(uri))).thenReturn("123");
        when(meetingGroupRepository.findById(eq("123"))).thenReturn(Optional.of(meetingGroup));
        when(suggestionFactory.getNextSuggestions(eq(meetingGroup), eq(4)))
                .thenReturn(Arrays.asList(suggestion, suggestion));

        assertThat(testee.getOrCreateSuggestions(options, pageable)).hasSize(2);

        verify(suggestionRepository, never()).save(any());
        verify(suggestionRepository, never()).saveAll(any());
    }
}
