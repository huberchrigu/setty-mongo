package ch.chrigu.setty.mongo.domain.meetinggroup;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = MeetingGroup.class)
public interface MeetingGroupExcerpt {
    String getName();
}
