package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import ch.chrigu.setty.mongo.domain.suggestion.service.Vote;
import ch.chrigu.setty.mongo.domain.suggestion.service.SuggestionCreateOptions;
import ch.chrigu.setty.mongo.domain.suggestion.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author christoph.huber
 * @since 29.01.2018
 */
@RepositoryRestController
@AllArgsConstructor
@RequestMapping("/suggestions")
public class SuggestionController {
    private final SuggestionService suggestionService;
    private final PagedResourcesAssembler<Suggestion> suggestionPagedResourceAssembler;
    private final SuggestionModelAssembler suggestionModelAssembler;

    @GetMapping("/search/findNext")
    PagedModel<EntityModel<Suggestion>> findNext(@Valid SuggestionCreateOptions options, Pageable pageable) {
        final Page<Suggestion> page = suggestionService.getOrCreateSuggestions(options, pageable);
        return suggestionPagedResourceAssembler.toModel(page, suggestionModelAssembler);
    }

    @PostMapping("/{id}/votes")
    ResponseEntity<EntityModel<Suggestion>> vote(@PathVariable String id, @RequestBody @Validated Vote vote) {
        final Suggestion suggestion = suggestionService.vote(id, vote);
        return ResponseEntity.ok()
                .eTag(suggestion.getVersion().toString())
                .body(suggestionModelAssembler.toModel(suggestion));
    }
}
