package com.example.sample.security;

import com.example.sample.app.dto.UserDto;
import com.example.sample.constants.ErrorConstants;
import com.example.sample.exception.ApplicationRuntimeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SpringのSecurityContextにアクセスするユーティリティクラス
 *
 * @author YutaMori
 */
public class SecurityContextUtils {

    /**
     * プライベートコンストラクタ
     */
    private SecurityContextUtils() {
    }

    /**
     * UsernamePasswordAuthenticationTokenを取得する
     *
     * @return UsernamePasswordAuthenticationToken
     */
    public static UsernamePasswordAuthenticationToken getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        }
        return (UsernamePasswordAuthenticationToken) authentication;
    }

    /**
     * UserDtoを取得する
     *
     * @return UserDto
     */
    public static UserDto getUserDto() {
        UsernamePasswordAuthenticationToken authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (UserDto) authentication.getPrincipal();
    }

    /**
     * UserDtoを取得する
     * SecurityContextHolderから見つからない場合、例外をスローする
     *
     * @return UserDto
     */
    public static UserDto requiredUserDto() {
        UserDto userDto = getUserDto();
        if (userDto == null) {
            throw ApplicationRuntimeException.ofValidationError(ErrorConstants.S003);
        }
        return userDto;
    }

    /**
     * userIdを取得する
     *
     * @return userId
     */
    public static String getUserId() {
        UserDto userDto = getUserDto();
        if (userDto == null) {
            return null;
        }
        return userDto.getUserId();
    }

    /**
     * userIdを取得する
     * SecurityContextHolderから見つからない場合、例外をスローする
     *
     * @return userId
     */
    public static String requiredUserId() {
        String userId = getUserId();
        if (userId == null) {
            throw ApplicationRuntimeException.ofValidationError(ErrorConstants.S003);
        }
        return userId;
    }
}
