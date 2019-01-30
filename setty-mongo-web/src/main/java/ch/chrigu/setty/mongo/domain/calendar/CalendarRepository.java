package ch.chrigu.setty.mongo.domain.calendar;

import ch.chrigu.setty.mongo.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface CalendarRepository extends MongoRepository<UserCalendar, String> {
    List<UserCalendar> findByOwnerIn(Collection<User> owners);
}