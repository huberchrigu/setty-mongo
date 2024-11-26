package ch.chrigu.setty.mongo.domain.common;

import ch.chrigu.setty.mongo.domain.suggestion.CalendarEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalendarEntryTest {
	private static final OffsetDateTime SECOND_JANUARY =
			LocalDateTime.of(2017, 1, 2, 17, 30)
					.atOffset(ZoneOffset.UTC);
	@Mock
	private CalendarEntry otherEntry;

	@InjectMocks
	private CalendarEntry testee;

	@Test
	public void shouldOverlapWithEntry() throws Exception {
		testee = new CalendarEntry(LocalDateTime.of(2017, 1, 1, 1, 1).atOffset(ZoneOffset.UTC),
				SECOND_JANUARY);

		when(otherEntry.getFrom()).thenReturn(SECOND_JANUARY);
		when(otherEntry.getTo()).thenReturn(LocalDateTime.of(2017, 1, 2, 18, 0).atOffset(ZoneOffset.UTC));

		assertThat(testee.overlaps(otherEntry)).isTrue();
	}

	@Test
	public void shouldNotOverlapWhenOneMinuteBetween() throws Exception {
		testee = new CalendarEntry(LocalDateTime.of(2017, 1, 1, 1, 1).atOffset(ZoneOffset.UTC),
				SECOND_JANUARY);

		when(otherEntry.getFrom()).thenReturn(SECOND_JANUARY.plusMinutes(1));

		assertThat(testee.overlaps(otherEntry)).isFalse();
	}
}