package ch.chrigu.setty.mongo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity()
public class MethodSecurityConfig {
    static final String KEY = "run-as-key";

    @Bean
    public RunAsManager runAsManager() {
        return new AnnotationDrivenRunAsManager(KEY);
    }
}
