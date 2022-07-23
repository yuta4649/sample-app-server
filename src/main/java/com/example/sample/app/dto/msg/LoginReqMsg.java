package com.example.sample.app.dto.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * ログインリクエストのメッセージ
 *
 * @author YutaMori
 */
@Getter
@Setter
public class LoginReqMsg {

    /**
     * ログインID
     */
    private String loginId;

    /**
     * パスワード
     */
    private String password;

}
