package com.example.sample.domain.service;

import com.example.sample.domain.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User情報を扱うDomainServiceインターフェース
 *
 * @author YutaMori
 */
@Service
@Transactional
public interface UserDomainService {

    /**
     * Account情報を取得する
     *
     * @param userId ユーザーID
     * @return Users ユーザー情報
     */
    Users getAccountInfo(String userId);
}
