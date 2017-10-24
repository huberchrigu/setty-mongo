package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalTime;

/**
 * @author christoph.huber
 * @since 17.09.2017
 */
@Getter
@AllArgsConstructor
class TimeSpan {
    @JsonProperty(required = true)
    private LocalTime from;

    @JsonProperty(required = true)
    private LocalTime to;

    @PositiveOrZero
    private int days;
}