package com.example.sample.domain.service.impl;


import com.example.sample.domain.entity.User;
import com.example.sample.domain.repository.UserRepository;
import com.example.sample.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * UserDomainServiceのimplements
 *
 * @author YutaMori
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDomainServiceImpl implements UserDomainService {

    /**
     * UserRepository
     */
    private final UserRepository userRepository;

    /**
     * メールアドレスからUser情報を取得する
     *
     * @param mailAddress メールアドレス
     * @return User
     */
    @Override
    public User getUserByMailAddress(String mailAddress) {
        return userRepository.getUserByMailAddress(mailAddress).orElseThrow();
    }
}
