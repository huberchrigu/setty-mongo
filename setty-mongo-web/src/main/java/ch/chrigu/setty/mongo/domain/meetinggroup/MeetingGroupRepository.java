package ch.chrigu.setty.mongo.domain.meetinggroup;

import ch.chrigu.setty.mongo.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author christoph.huber
 * @since 20.10.2017
 */
public interface MeetingGroupRepository extends MongoRepository<MeetingGroup, String> {
    List<MeetingGroup> findAllByMembers(User member);
}
