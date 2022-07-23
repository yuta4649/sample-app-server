package com.example.sample.security;

import com.example.sample.app.dto.UserDto;
import com.example.sample.app.dto.msg.LoginRespMsg;
import com.example.sample.utils.JacksonHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 認証成功時のハンドラー
 * JsonとしてResponseBodyを書き込む
 *
 * @author YutaMori
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * JacksonHelper
     */
    private final JacksonHelper jacksonHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDto userDto = (UserDto) authentication.getPrincipal();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jacksonHelper.getDefaultObjectMapper().writeValueAsString(new LoginRespMsg(userDto)));
        response.getWriter().flush();
    }
}
