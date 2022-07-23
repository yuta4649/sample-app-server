package com.example.sample.security;

import com.example.sample.utils.JacksonHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * spring-security関連の設定
 *
 * @author YutaMori
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * CustomAuthenticationProvider
     */
    private final CustomAuthenticationProvider customAuthenticationProvider;

    /**
     * CustomAuthenticationSuccessHandler
     */
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /**
     * CustomAuthenticationFailureHandler
     */
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * CustomLogoutSuccessHandler
     */
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    /**
     * SessionAuthenticationStrategy
     */
    private final SessionAuthenticationStrategy sessionAuthenticationStrategy;

    /**
     * JacksonHelper
     */
    private final JacksonHelper jacksonHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationProcessingFilter authenticationProcessingFilter =
                new CustomAuthenticationProcessingFilter(jacksonHelper);
        authenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        authenticationProcessingFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        authenticationProcessingFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        authenticationProcessingFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);

        http
                .csrf().disable()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/login").permitAll()
                        .antMatchers("/public/**").permitAll()
                        .antMatchers("/sample/**").permitAll()
                        .antMatchers("/app/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(
                                new AntPathRequestMatcher("/logout","POST")
                        )
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                )
                .addFilter(authenticationProcessingFilter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }
}
