package com.example.sample.exception;

import com.example.sample.utils.ErrorUtils;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * アプリケーションで扱う例外
 *
 * @author YutaMori
 */
@Getter
public class ApplicationRuntimeException extends RuntimeException {

    /**
     * エラー情報のリスト
     */
    private final List<ObjectError> errors;

    /**
     * バリデーションエラーフラグ
     */
    private final boolean validationError;

    /**
     * コンストラクタ
     *
     * @param errors エラー情報のリスト
     * @param validationError バリデーションエラーフラグ
     */
    public ApplicationRuntimeException(List<ObjectError> errors, boolean validationError) {
        super();
        this.errors = errors;
        this.validationError = validationError;
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージ
     * @param errors エラー情報のリスト
     * @param validationError バリデーションエラーフラグ
     */
    public ApplicationRuntimeException(String message, List<ObjectError> errors, boolean validationError) {
        super(message);
        this.errors = errors;
        this.validationError = validationError;
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージ
     * @param cause ラップする例外
     * @param errors エラー情報のリスト
     * @param validationError バリデーションエラーフラグ
     */
    public ApplicationRuntimeException(String message,Throwable cause, List<ObjectError> errors, boolean validationError) {
        super(message, cause);
        this.errors = errors;
        this.validationError = validationError;
    }

    /**
     * コンストラクタ
     *
     * @param cause ラップする例外
     * @param errors エラー情報のリスト
     * @param validationError バリデーションエラーフラグ
     */
    public ApplicationRuntimeException(Throwable cause, List<ObjectError> errors, boolean validationError) {
        super(cause);
        this.errors = errors;
        this.validationError = validationError;
    }

    /**
     * システムエラーのインスタンスを生成する
     *
     * @param errorCode エラーコード
     * @return ApplicationRuntimeException
     */
    public static ApplicationRuntimeException ofSystemError(String errorCode) {
        List<ObjectError> errors = ImmutableList.of(ErrorUtils.createObjectError(errorCode));
        return new ApplicationRuntimeException(errors, false);
    }

    /**
     * システムエラーのインスタンスを生成する
     *
     * @param errorCode エラーコード
     * @param argument 引数
     * @return ApplicationRuntimeException
     */
    public static ApplicationRuntimeException ofSystemError(String errorCode, Object[] argument) {
        List<ObjectError> errors = ImmutableList.of(ErrorUtils.createObjectError(errorCode, argument));
        return new ApplicationRuntimeException(errors, false);
    }

    /**
     * バリデーションエラーのインスタンスを生成する
     *
     * @param errorCode エラーコード
     * @return ApplicationRuntimeException
     */
    public static ApplicationRuntimeException ofValidationError(String errorCode) {
        List<ObjectError> errors = ImmutableList.of(ErrorUtils.createObjectError(errorCode));
        return new ApplicationRuntimeException(errors, true);
    }

    /**
     * バリデーションエラーのインスタンスを生成する
     *
     * @param errorCode エラーコード
     * @param arguments 引数
     * @return ApplicationRuntimeException
     */
    public static ApplicationRuntimeException ofValidationError(String errorCode, Object[] arguments) {
        List<ObjectError> errors = ImmutableList.of(ErrorUtils.createObjectError(errorCode, arguments));
        return new ApplicationRuntimeException(errors, true);
    }
}
