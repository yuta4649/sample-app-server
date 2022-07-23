package com.example.sample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FlywayConfigファイル
 *
 * @author YutaMori
 */
@Slf4j
@Configuration
public class FlywayConfig {

    @Bean
    public FlywayMigrationStrategy strategy() {
        return flyway -> {
            // initData.bat ファイルを実行してDBを制御するため処理しない
            log.info("skip Startup flywayMigration");
        };
    }
}
