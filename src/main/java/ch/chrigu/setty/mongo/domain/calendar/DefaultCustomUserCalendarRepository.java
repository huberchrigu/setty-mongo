package ch.chrigu.setty.mongo.domain.calendar;

import ch.chrigu.setty.mongo.domain.user.User;
import ch.chrigu.setty.mongo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DefaultCustomUserCalendarRepository implements CustomUserCalendarRepository {
    private final UserRepository userRepository;
    private final UserCalendarRepository userCalendarRepository;

    @Override
    public List<UserCalendar> findByOwnerCreatedBy(String createdBy) {
        final List<User> users = userRepository.findByCreatedBy(createdBy);
        return userCalendarRepository.findByOwnerIn(users);
    }
}
