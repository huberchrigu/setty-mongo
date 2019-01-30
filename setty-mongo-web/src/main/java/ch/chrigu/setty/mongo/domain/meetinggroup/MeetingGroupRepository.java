package ch.chrigu.setty.mongo.domain.meetinggroup;

import ch.chrigu.setty.mongo.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author christoph.huber
 * @since 20.10.2017
 */
@RepositoryRestResource(excerptProjection = MeetingGroupExcerpt.class)
public interface MeetingGroupRepository extends MongoRepository<MeetingGroup, String> {
    List<MeetingGroup> findAllByMembers(User member);
}
