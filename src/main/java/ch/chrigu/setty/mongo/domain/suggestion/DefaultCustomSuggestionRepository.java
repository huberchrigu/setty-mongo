package ch.chrigu.setty.mongo.domain.suggestion;

import ch.chrigu.setty.mongo.domain.meetinggroup.CustomMeetingGroupRepository;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DefaultCustomSuggestionRepository implements CustomSuggestionRepository {
    private final CustomMeetingGroupRepository meetingGroupRepository;
    private final SuggestionRepository suggestionRepository;

    @Override
    public List<Suggestion> findByForGroupMembersCreatedBy(String createdBy) {
        List<MeetingGroup> groups = meetingGroupRepository.findByMembersCreatedBy(createdBy);
        return suggestionRepository.findByForGroupIn(groups);
    }
}
