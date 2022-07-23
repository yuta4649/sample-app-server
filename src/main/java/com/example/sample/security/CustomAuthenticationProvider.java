package com.example.sample.security;

import com.example.sample.app.dto.msg.LoginReqMsg;
import com.example.sample.app.service.UserAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * AuthenticationProvider
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /**
     * UserAppService
     */
    private final UserAppService userAppService;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        LoginReqMsg loginReqMsg = (LoginReqMsg) authentication.getPrincipal();
        if (loginReqMsg == null) {
            throw new BadCredentialsException("LoginReqMsg is not found");
        }
        return userAppService.login(loginReqMsg);
    }
}
