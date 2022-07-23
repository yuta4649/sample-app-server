package com.example.sample.controller;


import com.example.sample.app.service.UserAppService;
import com.example.sample.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * User関連のコントローラー
 *
 * @author YutaMori
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {

    /**
     * UserAppService
     */
    private final UserAppService userAppService;

    /**
     * メールアドレスからUser情報を取得する
     *
     * @param mailAddress メールアドレス
     * @return User
     */
    @GetMapping("/{mailAddress}")
    @ResponseBody
    public User getUserByMailAddress(@PathVariable String mailAddress) {
        return userAppService.getUserByMailAddress(mailAddress);
    }
}
