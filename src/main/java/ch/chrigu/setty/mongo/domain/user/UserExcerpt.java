package ch.chrigu.setty.mongo.domain.user;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = User.class)
public interface UserExcerpt {
    String getFirstName();
    String getLastName();
}
