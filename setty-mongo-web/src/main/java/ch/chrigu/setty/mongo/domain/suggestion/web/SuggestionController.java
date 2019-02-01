package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import ch.chrigu.setty.mongo.domain.suggestion.service.Vote;
import ch.chrigu.setty.mongo.domain.suggestion.service.SuggestionCreateOptions;
import ch.chrigu.setty.mongo.domain.suggestion.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author christoph.huber
 * @since 29.01.2018
 */
@RestController
@AllArgsConstructor
@RequestMapping("/suggestions")
@ExposesResourceFor(Suggestion.class)
public class SuggestionController {
    private final SuggestionService suggestionService;
    private final PagedResourcesAssembler<Suggestion> suggestionPagedResourceAssembler;
    private final SuggestionResourceAssembler suggestionResourceAssembler; // TODO: This assembler should use the regular resource processor

    @GetMapping("/search/findNext")
    Resources<Resource<Suggestion>> findNext(@Valid SuggestionCreateOptions options, Pageable pageable) {
        final Page<Suggestion> page = suggestionService.getOrCreateSuggestions(options, pageable);
        return suggestionPagedResourceAssembler.toResource(page, suggestionResourceAssembler);
    }

    @PostMapping("/{id}/votes")
    Resource<Suggestion> vote(@PathVariable String id, @RequestBody @Validated Vote vote) {
        final Suggestion suggestion = suggestionService.vote(id, vote);
        return suggestionResourceAssembler.toResource(suggestion);
    }
}
