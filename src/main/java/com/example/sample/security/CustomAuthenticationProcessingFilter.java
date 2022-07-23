package com.example.sample.security;

import com.example.sample.app.dto.msg.LoginReqMsg;
import com.example.sample.utils.JacksonHelper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * JDON形式のリクエストに対応したUsernamePasswordAuthenticationFilter
 * ログインURLは親クラスのコンストラクタが呼ばれ、/loginが設定される
 *
 * @author YutaMori
 */
public class CustomAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * JacksonHelper
     */
    private final JacksonHelper jacksonHelper;

    /**
     * コンストラクタ
     *
     * @param jacksonHelper JacksonHelper
     */
    public CustomAuthenticationProcessingFilter(JacksonHelper jacksonHelper) {
        super();
        this.jacksonHelper = jacksonHelper;
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        // POSTメソッドのみ
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // ContentTypeはJSON
        if (!StringUtils.startsWith(request.getHeader(HttpHeaders.CONTENT_TYPE).toLowerCase(Locale.getDefault()),
                MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException("invalid Content-Type");
        }

        LoginReqMsg loginReqMsg = jacksonHelper.getDefaultObjectMapper()
                .readValue(request.getReader(), LoginReqMsg.class);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginReqMsg, null);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
