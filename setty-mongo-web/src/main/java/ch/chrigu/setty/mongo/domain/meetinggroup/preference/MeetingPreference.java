package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import ch.chrigu.setty.mongo.domain.declaration.RequiredForDeserialization;
import ch.chrigu.setty.mongo.domain.suggestion.CalendarEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Getter
@EqualsAndHashCode
public class MeetingPreference {
    @JsonProperty(required = true)
    private DayOfWeek day;

    @JsonProperty(required = true)
    private TimeSpan timeSpan;

    @JsonIgnore
    private WeekFields weekFields = WeekFields.of(Locale.getDefault());

    @RequiredForDeserialization
    MeetingPreference() {}

    MeetingPreference(DayOfWeek day, TimeSpan timeSpan) {
        this.day = day;
        this.timeSpan = timeSpan;
    }

    public CalendarEntry toCalendarEntry(int year, int week) {
        LocalDate from = LocalDate.now().withYear(year).with(weekFields.weekOfYear(), week).with(weekFields.dayOfWeek(), day.getValue());
        LocalDate to = from.plusDays(timeSpan.getDays());

        return new CalendarEntry(from.atTime(timeSpan.getFrom()), to.atTime(timeSpan.getTo()));
    }
}