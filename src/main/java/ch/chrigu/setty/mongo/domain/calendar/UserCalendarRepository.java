package ch.chrigu.setty.mongo.domain.calendar;

import ch.chrigu.setty.mongo.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasRole('ADMIN')")
public interface UserCalendarRepository extends MongoRepository<UserCalendar, String> {
    @PreAuthorize("hasRole('ADMIN') || @currentUserService.areAllCreatedByCurrentUser(#owners)")
    List<UserCalendar> findByOwnerIn(Collection<User> owners);

    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') || @currentUserService.isNonNullAndCreatedByCurrentUser(returnObject.orElse(null))")
    @Override
    Optional<UserCalendar> findById(String id);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCreatedByCurrentUser(#userCalendar)")
    @Override
    <S extends UserCalendar> S save(S userCalendar);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCreatedByCurrentUser(#userCalendar)")
    @Override
    void delete(UserCalendar userCalendar);
}
