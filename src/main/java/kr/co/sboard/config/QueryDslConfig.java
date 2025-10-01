package kr.co.sboard.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    @PersistenceContext // EntityManager 주입
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory JPAQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
