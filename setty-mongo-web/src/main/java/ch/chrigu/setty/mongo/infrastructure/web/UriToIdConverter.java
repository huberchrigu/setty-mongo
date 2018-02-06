package ch.chrigu.setty.mongo.infrastructure.web;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * This can probably be done with Spring only, but I could not find a way how yet.
 *
 * @author christoph.huber
 * @since 29.01.2018
 */
@Component
public class UriToIdConverter {
    public String convert(@NotNull URI uri) {
        final String path = uri.getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
