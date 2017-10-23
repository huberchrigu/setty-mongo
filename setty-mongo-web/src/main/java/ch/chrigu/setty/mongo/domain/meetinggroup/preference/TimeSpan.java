package ch.chrigu.setty.mongo.domain.meetinggroup.preference;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

/**
 * @author christoph.huber
 * @since 17.09.2017
 */
@Getter
@AllArgsConstructor
public class TimeSpan {
    private LocalTime from;
    private LocalTime to;
    private int days;
}