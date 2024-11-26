package ch.chrigu.setty.mongo.domain.meetinggroup;

import ch.chrigu.setty.mongo.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

/**
 * @author christoph.huber
 * @since 20.10.2017
 */
@RepositoryRestResource(excerptProjection = MeetingGroupExcerpt.class)
@PreAuthorize("hasRole('ADMIN')")
public interface MeetingGroupRepository extends MongoRepository<MeetingGroup, String> {

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCreatedByCurrentUser(#member)")
    List<MeetingGroup> findByMembers(User member);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.areAllCreatedByCurrentUser(#members)")
    List<MeetingGroup> findByMembersIn(List<User> members);

    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') || @currentUserService.isNonNullAndRelatedToCurrentUser(returnObject.orElse(null))")
    @Override
    Optional<MeetingGroup> findById(String id);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isNewOrCreatedByCurrentUser(#meetingGroup)")
    @Override
    <S extends MeetingGroup> S save(S meetingGroup);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCreatedByCurrentUser(#meetingGroup)")
    @Override
    void delete(MeetingGroup meetingGroup);
}
