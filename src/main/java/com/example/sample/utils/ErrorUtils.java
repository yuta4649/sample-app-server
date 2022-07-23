package com.example.sample.utils;

import org.springframework.validation.ObjectError;

/**
 * エラーに関するユーティリティ
 *
 * @author YutaMori
 */
public class ErrorUtils {

    /**
     * プライベートコンストラクタ
     */
    private ErrorUtils() {
    }

    /**
     * Objectエラーを生成する
     *
     * @param errorCode エラーコード
     * @return ObjectError
     */
    public static ObjectError createObjectError(String errorCode) {
        return createObjectError(errorCode, null);
    }

    /**
     * Objectエラーを生成する
     *
     *
     */
    public static ObjectError createObjectError(String errorCode, Object[] arguments) {
        return new ObjectError("GlobalErrors", new String[]{errorCode}, arguments, errorCode);
    }
}
