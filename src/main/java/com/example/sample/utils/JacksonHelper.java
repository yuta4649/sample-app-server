package com.example.sample.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Jacksonに関するヘルパークラス
 *
 * @author YutaMori
 */
@Component
public class JacksonHelper {

    @Getter
    private ObjectMapper defaultObjectMapper;

    /**
     * 初期化処理
     */
    @PostConstruct
    public void postConstruct() {
        defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
