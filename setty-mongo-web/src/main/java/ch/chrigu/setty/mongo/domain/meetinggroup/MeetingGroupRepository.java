package ch.chrigu.setty.mongo.domain.meetinggroup;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author christoph.huber
 * @since 20.10.2017
 */
public interface MeetingGroupRepository extends MongoRepository<MeetingGroup, String> {
}
