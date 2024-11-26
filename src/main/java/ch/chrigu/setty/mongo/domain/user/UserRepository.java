package ch.chrigu.setty.mongo.domain.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = UserExcerpt.class)
@PreAuthorize("hasRole('ADMIN')")
public interface UserRepository extends MongoRepository<User, String> {
    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCurrentUser(#createdBy)")
    List<User> findByCreatedBy(String createdBy);

    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') || @currentUserService.isNonNullAndCreatedByCurrentUser(returnObject.orElse(null))")
    @Override
    Optional<User> findById(String s);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isNewOrCreatedByCurrentUser(#user)")
    @Override
    <S extends User> S save(S user);

    @PreAuthorize("hasRole('ADMIN') || @currentUserService.isCreatedByCurrentUser(#user)")
    @Override
    void delete(User user);
}
