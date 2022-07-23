package com.example.sample.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ログアウト成功時のハンドラー
 *
 * @author YutaMori
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 形式的なReqMsgのみ返却する
     *
     * @param request request
     * @param response response
     * @param authentication authentication
     * @throws IOException IOException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        // 空のJSONをwriteする
        response.getWriter().write("{}");
        response.getWriter().flush();
    }

}
