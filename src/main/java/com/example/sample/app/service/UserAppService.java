package com.example.sample.app.service;


import com.example.sample.app.dto.msg.LoginReqMsg;
import com.example.sample.domain.entity.Users;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Userの情報を扱うAppServiceインターフェース
 *
 * @author YutaMori
 */
@Service
@Transactional
public interface UserAppService {

    /**
     * ログインする
     *
     * @param loginReqMsg リクエスト
     * @return UsernamePasswordAuthenticationToken
     */
    UsernamePasswordAuthenticationToken login(LoginReqMsg loginReqMsg);

    /**
     * Account情報を取得する
     *
     * @param userId UserID
     * @return Users ユーザー情報
     */
    Users getAccountInfo(String userId);
}
