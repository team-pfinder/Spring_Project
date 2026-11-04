package com.lookation.global.mail

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

class LoggingMailSender : MailSender {
    override fun sendVerificationCode(email: String, code: String, purpose: String) {
        logger.info("verification code for {} ({}): {}", email, purpose, code)
    }

    private companion object {
        private val logger = LoggerFactory.getLogger(LoggingMailSender::class.java)
    }
}

class SmtpMailSender(private val javaMailSender: JavaMailSender) : MailSender {
    override fun sendVerificationCode(email: String, code: String, purpose: String) {
        val message = SimpleMailMessage().apply {
            setTo(email)
            setSubject("Lookation 인증 코드")
            setText("인증 코드: $code\n10분 안에 입력해 주세요.")
        }
        javaMailSender.send(message)
    }
}

@Configuration
class MailConfig {

    // Not named `mailSender`: Boot registers its JavaMailSender under that name when spring.mail.host is set.
    @Bean
    fun lookationMailSender(
        environment: Environment,
        javaMailSender: ObjectProvider<JavaMailSender>,
    ): MailSender =
        if (!environment.getProperty("spring.mail.host").isNullOrBlank()) {
            SmtpMailSender(javaMailSender.getObject())
        } else {
            LoggingMailSender()
        }
}
