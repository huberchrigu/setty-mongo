package ch.chrigu.setty.mongo.domain.suggestion;

import java.util.List;

public interface CustomSuggestionRepository {
    List<Suggestion> findByForGroupMembersCreatedBy(String createdBy);
}
