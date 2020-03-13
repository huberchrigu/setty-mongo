package ch.chrigu.setty.mongo.security;

import ch.chrigu.setty.mongo.domain.user.User;
import ch.chrigu.setty.mongo.domain.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isNonNullAndCreatedByCurrentUser(User user) {
        return user != null && isCreatedByCurrentUser(user);
    }

    public boolean isCreatedByCurrentUser(User user) {
        return isCurrentUser(user.getCreatedBy());
    }

    public boolean isCurrentUser(String userName) {
        final Authentication authentication = getAuthentication();
        return authentication.isAuthenticated() && authentication.getName().equals(userName);
    }

    public boolean isNewOrCreatedByCurrentUser(User user) {
        return user.getId() == null || isCreatedByCurrentUser(user);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
