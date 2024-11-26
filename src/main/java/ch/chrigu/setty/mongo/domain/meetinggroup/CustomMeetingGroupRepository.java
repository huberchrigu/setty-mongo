package ch.chrigu.setty.mongo.domain.meetinggroup;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CustomMeetingGroupRepository {

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCurrentUser(#createdBy)")
    List<MeetingGroup> findByMembersCreatedBy(String createdBy);
}
