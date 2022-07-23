package com.example.sample.config;


import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * セッションの設定config
 *
 * @author YutaMori
 */
@Configuration
@ConditionalOnWebApplication
public class SessionConfig {

    /**
     * SessionRepository
     *
     * @param jdbcTemplate JdbcTemplate
     * @param transactionTemplate TransactionTemplate
     * @return SessionRepository
     */
    @Bean
    public JdbcIndexedSessionRepository sessionRepository(
            JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        JdbcIndexedSessionRepository sessionRepository = new JdbcIndexedSessionRepository(jdbcTemplate, transactionTemplate);
        // TODO セッションタイムアウトをプロパティにする
        sessionRepository.setDefaultMaxInactiveInterval(24 * 60 * 60);
        return sessionRepository;
    }

    /**
     * SessionRegistryを返す
     *
     * @param sessionRepository SessionRepository
     * @return SessionRegistry
     */
    @Bean
    public SessionRegistry sessionRegistry(FindByIndexNameSessionRepository sessionRepository) {
        // JdbcIndexedSessionRepository.JdbcSession がパッケージプライベートでここからアクセスできないため
        // unchecked, rawtypes を抑制している
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

    /**
     * SessionAuthenticationStrategyを返す
     *
     *
     */
    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy(SessionRegistry sessionRegistry) {

        // セッション固定化攻撃対策のため、セッションを毎回生成する
        SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
        sessionFixationProtectionStrategy.setMigrateSessionAttributes(false);
        sessionFixationProtectionStrategy.setAlwaysCreateSession(true);

        return new CompositeSessionAuthenticationStrategy(Lists.newArrayList(sessionFixationProtectionStrategy));
    }
}
