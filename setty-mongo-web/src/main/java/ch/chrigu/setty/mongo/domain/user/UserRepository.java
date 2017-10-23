package ch.chrigu.setty.mongo.domain.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UserExcerpt.class)
public interface UserRepository extends MongoRepository<User, String> {
	
}
