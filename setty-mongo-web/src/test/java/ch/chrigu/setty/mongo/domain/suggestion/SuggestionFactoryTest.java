package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.calendar.CalendarRepository;
import ch.chrigu.setty.mongo.domain.calendar.UserCalendar;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.meetinggroup.preference.MeetingPreference;
import ch.chrigu.setty.mongo.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SuggestionFactoryTest {

    private static final int SUGGESTION_YEAR = 2017;
    private static final int SUGGESTION_WEEK = 9;

    @InjectMocks
    private SuggestionFactory suggestionFactory;

    @Mock
    private MeetingGroup meetingGroup;
    @Mock
    private MeetingPreference meetingPreference1;
    @Mock
    private CalendarEntry preferenceEntry1;
    @Mock
    private MeetingPreference meetingPreference2;
    @Mock
    private CalendarEntry preferenceEntry2;

    @Mock
    private User user1;
    @Mock
    private User user2;
    @Mock
    private UserCalendar userCalendar1;
    @Mock
    private UserCalendar userCalendar2;
    @Mock
    private CalendarRepository calendarRepository;

    @Test
    public void shouldCreateOneSuggestionWhenOneIsOccupied() {
        withMeetingGroup();
        withCalendar(userCalendar1, false, true);
        withCalendar(userCalendar2, false, false);

        List<Suggestion> suggestions = suggestionFactory.createSuggestionsFor(meetingGroup, SUGGESTION_YEAR, SUGGESTION_WEEK);

        assertThat(suggestions.size()).isEqualTo(1);
        assertThat(suggestions.get(0).getCalendarEntry()).isEqualTo(preferenceEntry1);
    }

    private void withCalendar(UserCalendar userCalendar, boolean preference1Overlaps, boolean preference2Overlaps) {
        CalendarEntry entry = mock(CalendarEntry.class);
        when(entry.overlaps(preferenceEntry1)).thenReturn(preference1Overlaps);
        when(entry.overlaps(preferenceEntry2)).thenReturn(preference2Overlaps);
        when(userCalendar.getEntries()).thenReturn(Collections.singleton(entry));
    }

    private void withMeetingGroup() {
        when(meetingGroup.getPreferences()).thenReturn(Arrays.asList(meetingPreference1, meetingPreference2));
        when(meetingPreference1.toCalendarEntry(SUGGESTION_YEAR, SUGGESTION_WEEK)).thenReturn(preferenceEntry1);
        when(meetingPreference2.toCalendarEntry(SUGGESTION_YEAR, SUGGESTION_WEEK)).thenReturn(preferenceEntry2);

        Set<User> users = Sets.newSet(user1, user2);
        when(meetingGroup.getMembers()).thenReturn(users);
        when(calendarRepository.findByOwnerIn(users)).thenReturn(Arrays.asList(userCalendar1, userCalendar2));
    }
}