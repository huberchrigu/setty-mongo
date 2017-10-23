package ch.chrigu.setty.mongo.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.chrigu.setty.mongo.domain.aggregate.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User extends AggregateRoot {

	@JsonProperty(required = true)
	private String lastName;
	
	@JsonProperty(required = true)
	private String firstName;
}