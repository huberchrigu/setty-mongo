package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.calendar.UserCalendarRepository;
import ch.chrigu.setty.mongo.domain.calendar.UserCalendar;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class SuggestionFactory {
    private final UserCalendarRepository userCalendarRepository;
    private final WeekFields weekFields = WeekFields.of(Locale.getDefault());

    public SuggestionFactory(UserCalendarRepository userCalendarRepository) {
        this.userCalendarRepository = userCalendarRepository;
    }

    public List<Suggestion> getNextSuggestions(@NotNull MeetingGroup meetingGroup, int numOfWeeks) {
        OffsetDateTime now = OffsetDateTime.now();
        return IntStream.range(0, numOfWeeks)
                .mapToObj(now::plusWeeks)
                .flatMap(date -> createSuggestions(meetingGroup, date.getYear(), date.get(weekFields.weekOfYear())))
                .filter(date -> date.getCalendarEntry().getFrom().isAfter(now))
                .collect(Collectors.toList());
    }

    public List<Suggestion> createSuggestionsFor(@NotNull MeetingGroup meetingGroup, int year, int week) {
        return createSuggestions(meetingGroup, year, week).collect(Collectors.toList());
    }

    private Stream<Suggestion> createSuggestions(@NotNull MeetingGroup meetingGroup, int year, int week) {
        List<UserCalendar> userCalendars = userCalendarRepository.findByOwnerIn(meetingGroup.getMembers());
        return meetingGroup.getPreferences().stream()
                .map(p -> p.toCalendarEntry(year, week))
                .filter(entry -> isNotOccupied(entry, userCalendars))
                .map(entry -> new Suggestion(meetingGroup, entry));
    }

    private boolean isNotOccupied(CalendarEntry entry, List<UserCalendar> userCalendars) {
        for (UserCalendar calendar : userCalendars) {
            if (calendar.getEntries().stream().anyMatch(userEntry -> userEntry.overlaps(entry))) {
                return false;
            }
        }
        return true;
    }
}
