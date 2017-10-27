package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalTime;

@Getter
public class TimeSpan {
    @JsonProperty(required = true)
    private LocalTime from;

    @JsonProperty(required = true)
    private LocalTime to;

    private int days;

    public TimeSpan(LocalTime from, LocalTime to, int days) {
        Assert.notNull(from, "The time should not be null");
        Assert.notNull(to, "The time should not be null");
        Assert.state(days >= 0, "The time span day duration cannot be negative");
        Assert.state(from.isBefore(to), "'from' has to be before 'to'");

        this.from = from;
        this.to = to;
        this.days = days;
    }
}