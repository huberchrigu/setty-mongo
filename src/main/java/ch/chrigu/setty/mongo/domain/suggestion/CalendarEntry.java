package ch.chrigu.setty.mongo.domain.suggestion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CalendarEntry {

	@JsonProperty(required = true)
	private OffsetDateTime from;

	@JsonProperty(required = true)
	private OffsetDateTime to;

	public boolean overlaps(CalendarEntry entry) {
		return (from.compareTo(entry.getFrom()) >= 0 && from.compareTo(entry.getTo()) <= 0) ||
				(to.compareTo(entry.getFrom()) >= 0 && to.compareTo(entry.getTo()) <= 0);
	}
}
