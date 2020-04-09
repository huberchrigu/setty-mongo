package ch.chrigu.setty.mongo.domain.meetinggroup;

import ch.chrigu.setty.mongo.domain.user.User;
import ch.chrigu.setty.mongo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DefaultCustomMeetingGroupRepository implements CustomMeetingGroupRepository {
    private final UserRepository userRepository;
    private final MeetingGroupRepository meetingGroupRepository;

    @Override
    public List<MeetingGroup> findByMembersCreatedBy(String createdBy) {
        List<User> users = userRepository.findByCreatedBy(createdBy);
        return meetingGroupRepository.findByMembersIn(users);
    }
}
