package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import ch.chrigu.setty.mongo.domain.suggestion.CalendarEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Transient;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Getter
@AllArgsConstructor
public class MeetingPreference {
    @JsonProperty(required = true)
    @NonNull
    private DayOfWeek day;

    @JsonProperty(required = true)
    @NonNull
    private TimeSpan timeSpan;

    @JsonIgnore
    @Transient
    private final WeekFields weekFields = WeekFields.of(Locale.getDefault());

    public CalendarEntry toCalendarEntry(int year, int week) {
        LocalDate from = LocalDate.now().withYear(year).with(weekFields.weekOfYear(), week).with(ChronoField.DAY_OF_WEEK, day.getValue());
        LocalDate to = from.plusDays(timeSpan.getDays());
        return new CalendarEntry(from.atTime(timeSpan.getFrom()), to.atTime(timeSpan.getTo()));
    }
}
