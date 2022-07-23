package com.example.sample.app.dto.msg;

import com.example.sample.app.dto.ErrorDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * エラー発生時のレスポンスメッセージ
 *
 * @author YutaMori
 */
@Getter
@Setter
public class ErrorRespMsg {

    /**
     * エラー情報
     */
    private List<ErrorDto> errors;

    /**
     * エラーのリストからインスタンスを生成する
     *
     * @param errors エラーリスト
     * @param messageSource MessageSource
     * @return ErrorRespMsg
     */
    public static ErrorRespMsg ofErrorList(List<ObjectError> errors, MessageSource messageSource) {
        ErrorRespMsg errorRespMsg = new ErrorRespMsg();
        errorRespMsg.setErrors(errors.stream().map((error) -> {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode(error.getCode());
            errorDto.setMessage(messageSource.getMessage(error, Locale.getDefault()));
            return errorDto;
        }).collect(Collectors.toList()));
        return errorRespMsg;
    }
}
