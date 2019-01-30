package ch.chrigu.setty.mongo.domain.meetinggroup;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MeetingGroupUpdatedEvent {
    private final MeetingGroup meetingGroup;
}
