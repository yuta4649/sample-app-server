package com.example.sample.security;

import com.example.sample.app.dto.msg.ErrorRespMsg;
import com.example.sample.constants.ErrorConstants;
import com.example.sample.utils.ErrorUtils;
import com.example.sample.utils.JacksonHelper;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 認証失敗時のハンドラー
 *
 * @author YutaMori
 */
@RequiredArgsConstructor
@Slf4j
@Component
@ConditionalOnWebApplication
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * jacksonHelper
     */
    private final JacksonHelper jacksonHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // ログイン失敗時
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorCode = ErrorConstants.S000;
        if (exception instanceof BadCredentialsException) {

            switch (exception.getMessage()) {
                case "User not found":
                    status = HttpStatus.UNAUTHORIZED;
                    errorCode = ErrorConstants.S004;
                    log.warn("Auth error", exception);
                    break;
                default:
                    status = HttpStatus.UNAUTHORIZED;
                    errorCode = ErrorConstants.S001;
                    log.warn("Auth error", exception);
            }
        } else {
            log.error("Auth error", exception);
        }

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter(); // NOPMD - ServletResponseから取得するWriterは自動的にcloseされるので抑止

        List<ObjectError> errors = ImmutableList.of(ErrorUtils.createObjectError(errorCode));
        ErrorRespMsg respMsg = ErrorRespMsg.ofErrorList(errors, messageSource);
        jacksonHelper.getDefaultObjectMapper().writeValue(writer, respMsg);
        writer.flush();
    }
}
