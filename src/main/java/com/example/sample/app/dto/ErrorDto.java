package com.example.sample.app.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * エラー情報
 *
 * @author YutaMori
 */
@Getter
@Setter
public class ErrorDto {

    /**
     * エラーコード
     */
    private String code;

    /**
     * エラーメッセージ
     */
    private String message;

    // TODO エラーレベルなどを追加
}
