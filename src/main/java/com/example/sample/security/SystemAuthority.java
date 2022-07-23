package com.example.sample.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * システムで扱う権限の種類のEnum
 *
 * @author YutaMori
 */
@Getter
@RequiredArgsConstructor
public enum SystemAuthority implements GrantedAuthority {

    /**
     * 一般ユーザー
     */
    ROLE_USER("ROLE_USER"),

    /**
     * 管理ユーザー
     */
    ROLE_ADMIN("ROLE_ADMIN");

    /**
     * 権限の名前
     */
    private final String authority;
}
