package ch.chrigu.setty.mongo.domain.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCalendarUpdatedEvent {
    private final UserCalendar userCalendar;
}
