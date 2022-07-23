package com.example.sample.controller;

import com.example.sample.app.dto.msg.ErrorRespMsg;
import com.example.sample.constants.ErrorConstants;
import com.example.sample.utils.ErrorUtils;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 404例外などGlobalExceptionAdapterで捕捉されないエラーをクライアントに返却する
 * 通常のユーザー操作では発生しないため、脆弱性対策目的でデフォルトエラーページではなく最小限のエラー内容のみ返す
 *
 * @author Yuta Mori
 */
@ConditionalOnWebApplication
@RestController
@Slf4j
public class HttpErrorController extends AbstractErrorController {

    /**
     * エラーのパス。Springのデフォルト値
     */
    private static final String PATH = "/error";

    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * コンストラクタ
     *
     * @param errorAttributes エラー情報
     * @param messageSource MessageSource
     */
    @Autowired
    public HttpErrorController(ErrorAttributes errorAttributes,
                               MessageSource messageSource) {
        super(errorAttributes);
        this.messageSource = messageSource;
    }

    /**
     * エラー発生時にフォワードされるパス
     *
     * @return パス
     * TODO 削除予定
     */
    @Deprecated
    public String getErrorPath() {
        return PATH;
    }

    /**
     * Httpエラーのハンドリング
     *
     * @param request HTTPリクエスト
     * @return エラーメッセージ
     */
    @RequestMapping(value = PATH)
    @ResponseBody
    public ErrorRespMsg error(HttpServletRequest request) {
        // TODO 後で確認
        Map<String, Object> errorInfo = getErrorAttributes(request, null);

        // 設定されている内容は DefaultErrorAttributes の実装による
        Object status = errorInfo.get("status");
        // ログイン状態無効はS102、それ以外は通常のユーザー捜査では発生しないのでシステムエラーとしておく
        String errorCode = ErrorConstants.S000;
        if (status instanceof Integer && ((Integer) status) == 403) {
            errorCode = ErrorConstants.S002;
        }
        Object message = errorInfo.get("message");
        if (message == null) {
            message = "Unknown error";
        }

        log.warn("Http error {}, {}", status, message);

        // TODO 404の場合はindex.htmlにフォールバックする

        List<ObjectError> errors = ImmutableList.of(ErrorUtils.createObjectError(errorCode));
        return ErrorRespMsg.ofErrorList(errors, messageSource);
    }
}
