package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.OffsetTime;

@Getter
public class TimeSpan {
    @JsonUnwrapped
    @JsonProperty(required = true)
    private OffsetTime from;

    @JsonUnwrapped
    @JsonProperty(required = true)
    private OffsetTime to;

    private int days;

    public TimeSpan(OffsetTime from, OffsetTime to, int days) {
        Assert.notNull(from, "The time should not be null");
        Assert.notNull(to, "The time should not be null");
        Assert.state(days >= 0, "The time span day duration cannot be negative");
        Assert.state(from.isBefore(to), "'from' has to be before 'to'");

        this.from = from;
        this.to = to;
        this.days = days;
    }
}