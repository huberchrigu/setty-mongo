package ch.chrigu.setty.mongo.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Works only together with {@link org.springframework.security.access.prepost.PreAuthorize} which determines which users may access this method with enhanced permissions.
 * @see AnnotationDrivenRunAsManager
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunAsRole {
    String value();
}
