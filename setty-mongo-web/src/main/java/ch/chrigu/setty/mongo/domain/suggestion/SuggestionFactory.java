package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.calendar.UserCalendar;
import ch.chrigu.setty.mongo.domain.calendar.CalendarRepository;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SuggestionFactory {
    private final CalendarRepository calendarRepository;

    public SuggestionFactory(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    public List<Suggestion> createSuggestionsFor(@NotNull MeetingGroup meetingGroup, int year, int week) {
        List<UserCalendar> userCalendars = calendarRepository.findByOwner(meetingGroup.getMembers());
        return meetingGroup.getPreferences().stream()
                .map(p -> p.toCalendarEntry(year, week))
                .filter(entry -> isNotOccupied(entry, userCalendars))
                .map(entry -> new Suggestion(meetingGroup, entry))
                .collect(Collectors.toList());
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