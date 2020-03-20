package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import ch.chrigu.setty.mongo.domain.suggestion.CalendarEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Getter
@AllArgsConstructor
public class MeetingPreference {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingPreference.class);

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
        LocalDate from = LocalDate.now().withYear(year).with(weekFields.weekOfYear(), week).with(weekFields.dayOfWeek(), day.getValue());
        LOGGER.info("from: {}", from);
        LocalDate to = from.plusDays(timeSpan.getDays());
        LOGGER.info("timeSpan.getFrom(): {}", timeSpan.getFrom());
        return new CalendarEntry(from.atTime(timeSpan.getFrom()), to.atTime(timeSpan.getTo()));
    }
}
