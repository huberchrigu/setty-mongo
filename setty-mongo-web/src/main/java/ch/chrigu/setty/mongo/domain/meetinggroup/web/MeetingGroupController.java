package ch.chrigu.setty.mongo.domain.meetinggroup.web;

import ch.chrigu.setty.mongo.domain.meetinggroup.CustomMeetingGroupRepository;
import ch.chrigu.setty.mongo.domain.meetinggroup.MeetingGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestController
@RequestMapping("/meetingGroups")
@RequiredArgsConstructor
public class MeetingGroupController {
    private final CustomMeetingGroupRepository meetingGroupRepository;
    private final MeetingGroupAssembler meetingGroupAssembler;

    @GetMapping("/search/findByMembersCreatedBy")
    public ResponseEntity<CollectionModel<EntityModel<MeetingGroup>>> findByMembersCreatedBy(@RequestParam String createdBy) {
        List<MeetingGroup> result = meetingGroupRepository.findByMembersCreatedBy(createdBy);
        return ResponseEntity.ok(meetingGroupAssembler.toCollectionModel(result));
    }
}
