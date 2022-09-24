package com.example.sample.controller;


import com.example.sample.app.service.UserAppService;
import com.example.sample.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Account関連のコントローラー
 *
 * @author YutaMori
 */
@RestController
@RequestMapping("/app/account")
@RequiredArgsConstructor
public class AccountController {

    /**
     * UserAppService
     */
    private final UserAppService userAppService;

    /**
     * Account情報を取得
     *
     * @param userId UserID
     * @return Users ユーザー情報
     */
    @GetMapping("{userId}/info")
    @ResponseBody
    public Users getAccountInfo(@PathVariable String userId) {
        return userAppService.getAccountInfo(userId);
    }
}
