package com.example.sample.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

/**
 * FWレイヤーのセキュリティ設定
 *
 * @author YutaMori
 */
@Configuration
@ConditionalOnWebApplication
public class CoreWebSecurityConfig {

    /**
     * 標準のAuthenticationEntryPoint
     *
     * @return AuthenticationEntryPoint
     */
    @Bean("authenticationEntryPoint")
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    /**
     * HttpFirewall
     *
     * @return HttpFirewall
     */
    @Bean("firewall")
    public HttpFirewall firewall() {
        DefaultHttpFirewall httpFirewall = new DefaultHttpFirewall();
        httpFirewall.setAllowUrlEncodedSlash(true);
        return httpFirewall;
    }
}
