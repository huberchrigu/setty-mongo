package ch.chrigu.setty.mongo.security;

import ch.chrigu.setty.mongo.domain.aggregate.WithCreatedBy;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentUserService {
    public boolean isNonNullAndCreatedByCurrentUser(WithCreatedBy aggregateRoot) {
        return aggregateRoot != null && isCreatedByCurrentUser(aggregateRoot);
    }

    public boolean isCreatedByCurrentUser(WithCreatedBy aggregateRoot) {
        return isCurrentUser(aggregateRoot.getCreatedBy());
    }

    public boolean isCurrentUser(String userName) {
        final Authentication authentication = getAuthentication();
        return authentication.isAuthenticated() && authentication.getName().equals(userName);
    }

    public boolean isNewOrCreatedByCurrentUser(WithCreatedBy aggregateRoot) {
        return aggregateRoot.getId() == null || isCreatedByCurrentUser(aggregateRoot);
    }

    public boolean isNonNullAndRelatedToCurrentUser(MeetingGroup group) {
        return group != null && (isCreatedByCurrentUser(group) || group.getMembers().stream().anyMatch(this::isCreatedByCurrentUser));
    }

    public boolean areAllRelatedToCurrentUser(List<MeetingGroup> groups) {
        return groups == null || groups.stream().allMatch(this::isNonNullAndRelatedToCurrentUser);
    }

    public boolean isNonNullAndVisible(Suggestion suggestion) {
        return suggestion != null && isNonNullAndRelatedToCurrentUser(suggestion.getForGroup());
    }

    public boolean areAllCreatedByCurrentUser(List<WithCreatedBy> aggregateRoot) {
        return aggregateRoot == null || aggregateRoot.stream().allMatch(this::isCreatedByCurrentUser);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
