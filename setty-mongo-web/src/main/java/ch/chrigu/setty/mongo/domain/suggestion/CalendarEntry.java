package ch.chrigu.setty.mongo.domain.suggestion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CalendarEntry {

    @JsonProperty(required = true)
    private LocalDateTime from;

    @JsonProperty(required = true)
    private LocalDateTime to;

    public boolean overlaps(CalendarEntry entry) {
        return (from.compareTo(entry.getFrom()) >= 0 && from.compareTo(entry.getTo()) <= 0) ||
                (to.compareTo(entry.getFrom()) >= 0 && to.compareTo(entry.getTo()) <= 0);
    }
}