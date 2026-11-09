package com.lookation.global.mail

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

/**
 * 인증 메일 발송. SMTP 설정이 없으면 코드만 로그로 남긴다.
 * 이전 interface·구현 2종을 하나로 합쳤다. 새 전달 어댑터가 생기면 그때 추상화한다.
 */
open class MailSender(private val javaMailSender: JavaMailSender? = null) {

    open fun sendVerificationCode(email: String, code: String, purpose: String) {
        if (javaMailSender != null) {
            val message = SimpleMailMessage().apply {
                setTo(email)
                setSubject("Lookation 인증 코드")
                setText("인증 코드: $code\n10분 안에 입력해 주세요.")
            }
            javaMailSender.send(message)
        } else {
            logger.info("verification code for {} ({}): {}", email, purpose, code)
        }
    }

    private companion object {
        private val logger = LoggerFactory.getLogger(MailSender::class.java)
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
            MailSender(javaMailSender.getObject())
        } else {
            MailSender(null)
        }
}
