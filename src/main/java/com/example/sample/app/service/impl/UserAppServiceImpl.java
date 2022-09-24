package com.example.sample.app.service.impl;

import com.example.sample.app.dto.UserDto;
import com.example.sample.app.dto.msg.LoginReqMsg;
import com.example.sample.app.service.UserAppService;
import com.example.sample.domain.entity.Users;
import com.example.sample.domain.repository.UserRepository;
import com.example.sample.domain.service.UserDomainService;
import com.example.sample.security.SystemAuthority;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * UserAppServiceのimplements
 *
 * @author YutaMori
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserAppServiceImpl implements UserAppService {

    /**
     * UserDomainService
     */
    private final UserDomainService userDomainService;

    /**
     * UserRepository
     */
    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Users getAccountInfo(String userId) {
        return userDomainService.getAccountInfo(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsernamePasswordAuthenticationToken login(LoginReqMsg loginReqMsg) {

        Users user = userRepository.findByMailAddressOrLoginId(loginReqMsg.getLoginId()).orElseThrow(()
                -> new BadCredentialsException("User not found"));
        // ユーザーがロックされている場合処理を抜ける
        if (user.getLockedFlg().equals("1")) {
            throw new BadCredentialsException("locked account");
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            // 初期ログイン
            if (!Objects.equals(loginReqMsg.getPassword(), user.getInitialPassword())) {
                throw new BadCredentialsException("Password is incorrect");
            }
        } else {
            // 通常ログイン
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(loginReqMsg.getPassword(), user.getPassword()) && !loginReqMsg.getPassword().equals(user.getPassword())) {
                throw new BadCredentialsException("Password is incorrect");
            }
        }

        List<GrantedAuthority> authorities = Lists.newArrayList(SystemAuthority.ROLE_USER);
        if (user.getAuthorizationType().equals(SystemAuthority.ROLE_ADMIN.getAuthority())) {
            authorities.add(SystemAuthority.ROLE_ADMIN);
        }
        UserDto userDto = UserDto.of(user);
        return new UsernamePasswordAuthenticationToken(userDto, loginReqMsg.getPassword(), authorities);
    }
}
