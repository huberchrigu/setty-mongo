package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import ch.chrigu.setty.mongo.domain.suggestion.CalendarEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeetingPreferenceTest {

    private static final ZoneOffset TIMEZONE = ZoneOffset.UTC;

    @Mock
    private TimeSpan timeSpan;

    private MeetingPreference testee;

    @Test
    public void calendarEntryShouldBeOnOneDay() {
        withPreference(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0), 0);

        CalendarEntry expectedEntry = new CalendarEntry(
                LocalDateTime.of(2017, Month.JANUARY, 9, 19, 0).atOffset(TIMEZONE),
                LocalDateTime.of(2017, Month.JANUARY, 9, 22, 0).atOffset(TIMEZONE));
        assertThat(testee.toCalendarEntry(2017, 2)).isEqualTo(expectedEntry);
    }

    @Test
    public void calendarEntryShouldBeOnTwoDays() {
        withPreference(DayOfWeek.SUNDAY, LocalTime.of(20, 30), LocalTime.of(1, 0), 1);

        CalendarEntry expectedEntry = new CalendarEntry(
                LocalDateTime.of(2018, Month.MARCH, 11, 20, 30).atOffset(TIMEZONE),
                LocalDateTime.of(2018, Month.MARCH, 12, 1, 0).atOffset(TIMEZONE));
        assertThat(testee.toCalendarEntry(2018, 10)).isEqualTo(expectedEntry);
    }

    private void withPreference(DayOfWeek dayOfWeek, LocalTime from, LocalTime to, int days) {
        testee = new MeetingPreference(dayOfWeek, timeSpan);
        when(timeSpan.getFrom()).thenReturn(from.atOffset(TIMEZONE));
        when(timeSpan.getTo()).thenReturn(to.atOffset(TIMEZONE));
        when(timeSpan.getDays()).thenReturn(days);
    }
}
