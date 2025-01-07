package ch.chrigu.setty.mongo.domain.suggestion.web;

import ch.chrigu.setty.mongo.domain.suggestion.Suggestion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author christoph.huber
 * @since 01.02.2018
 */
@Component
public class SuggestionModelProcessor implements RepresentationModelProcessor<EntityModel<Suggestion>> {

    @Override
    public EntityModel<Suggestion> process(EntityModel<Suggestion> suggestionModel) {
        final Suggestion suggestion = suggestionModel.getContent();
        Assert.notNull(suggestion, "Cannot process empty suggestion");
        final String href = linkTo(methodOn(SuggestionController.class).vote(suggestion.getId(), null)).toString(); // FIXME: This link does not consider the RepositoryRestController annotation
        suggestionModel.add(Link.of(href, "vote"));
        return suggestionModel;
    }
}
