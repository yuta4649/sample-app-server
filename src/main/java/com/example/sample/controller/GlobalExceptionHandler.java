package com.example.sample.controller;

import com.example.sample.app.dto.msg.ErrorRespMsg;
import com.example.sample.constants.ErrorConstants;
import com.example.sample.exception.ApplicationRuntimeException;
import com.example.sample.utils.ErrorUtils;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 * 共通のエラーハンドラー
 *
 * @author Yuta Mori
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * アプリケーションレイヤーの例外ハンドリング
     * 応答はJSON形式で返却する
     * 画面遷移が必要な場合、異なるExceptionを用意
     *
     * @param exception 例外
     * @return レスポンス
     */
    @ExceptionHandler(ApplicationRuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorRespMsg> onApplicationError(ApplicationRuntimeException exception) {
        if (exception.isValidationError()) {
            log.warn("Validation error", exception);
        } else {
            log.error("System error", exception);
        }
        ErrorRespMsg errorRespMsg = ErrorRespMsg.ofErrorList(exception.getErrors(), messageSource);

        HttpStatus httpStatus = exception.isValidationError()
                ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(errorRespMsg, httpStatus);
    }

    /**
     * リクエストが想定外の形式の場合のハンドリング
     * 応答はJSON形式で返す
     * 画面遷移が必要な場合、異なるExceptionを用意
     *
     * @param exception 例外
     * @return ErrorRespMsg
     */
    @ExceptionHandler({BindException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespMsg handleBindException(Exception exception) {
        log.warn("Validation error {}", exception.getMessage());
        List<ObjectError> errors = ImmutableList.of(ErrorUtils.createObjectError(ErrorConstants.S001));
        return ErrorRespMsg.ofErrorList(errors, messageSource);
    }

    /**
     * その他の例外のハンドリング
     *
     *
     */
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRespMsg onOtherError(Exception exception) {
        log.error("System error {}", exception.getMessage());
        List<ObjectError> errors = ImmutableList.of(ErrorUtils.createObjectError(ErrorConstants.S000));
        return ErrorRespMsg.ofErrorList(errors, messageSource);
    }
}
