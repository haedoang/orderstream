package io.readingrecord.common.component

import io.readingrecord.common.config.SlackProperties
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class SlackComponent(
    private val slackProperties: SlackProperties,
    private val restTemplate: RestTemplate
) {
    private val logger = LoggerFactory.getLogger(SlackComponent::class.java)

    fun sendMessage(message: String, channel: String) {
        if (slackProperties.botToken.isBlank()) {
            logger.warn("Slack bot token is not configured. Message not sent: $message")
            return
        }

        if (channel.isBlank()) {
            logger.warn("Slack channel is not specified. Message not sent: $message")
            return
        }

        try {
            val headers = HttpHeaders().apply {
                set("Authorization", "Bearer ${slackProperties.botToken}")
                set("Content-Type", "application/json")
            }

            val payload = SlackMessage(
                channel = channel,
                text = message,
            )

            val request = HttpEntity(payload, headers)
            val url = "${slackProperties.apiUrl}/chat.postMessage"

            val response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                SlackResponse::class.java
            )

            val slackResponse = response.body
            if (slackResponse?.ok == true) {
                logger.info("Slack message sent successfully to $channel: $message")
            } else {
                logger.error("Failed to send Slack message: ${slackResponse?.error}")
            }
        } catch (error: Exception) {
            logger.error("Failed to send Slack message: $message", error)
        }
    }

    data class SlackMessage(
        val channel: String,
        val text: String,
    )

    data class SlackResponse(
        val ok: Boolean,
        val error: String? = null
    )
}
