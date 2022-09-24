package com.example.sample.domain.service.impl;


import com.example.sample.domain.entity.Users;
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
     * {@inheritDoc}
     */
    @Override
    public Users getAccountInfo(String userId) {
        return  userRepository.getAccountInfo(userId);
    }

}
