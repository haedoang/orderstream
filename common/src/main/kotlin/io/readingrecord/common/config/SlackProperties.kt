package io.readingrecord.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "slack")
data class SlackProperties(
    var botToken: String = "", // 환경변수로 주입
    var apiUrl: String = "https://slack.com/api"
)
