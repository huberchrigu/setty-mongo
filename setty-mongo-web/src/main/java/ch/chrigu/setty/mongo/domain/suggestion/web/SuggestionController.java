package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import ch.chrigu.setty.mongo.domain.suggestion.service.SuggestionCreateOptions;
import ch.chrigu.setty.mongo.domain.suggestion.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final PagedResourcesAssembler<Suggestion> pageAssembler;
    private final SuggestionResourceAssembler suggestionResourceAssembler;

    @GetMapping("/search/findNext")
    public Resources<Resource<Suggestion>> findNext(@Valid SuggestionCreateOptions options, Pageable pageable) {
        final Page<Suggestion> page = suggestionService.getOrCreateSuggestions(options, pageable);
        return pageAssembler.toResource(page, suggestionResourceAssembler);
    }
}
