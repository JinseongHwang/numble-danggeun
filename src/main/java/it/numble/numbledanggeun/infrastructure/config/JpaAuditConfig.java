package it.numble.numbledanggeun.infrastructure.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "defaultAuditorAware") // AuditorAware bean name
@Configuration
public class JpaAuditConfig {

    @Bean
    public AuditorAware<String> defaultAuditorAware() {
        return () -> Optional.of("default");
    }

    @PersistenceContext
    private EntityManager entityManager;

    // querydsl
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
