package com.lookation.integration

import com.lookation.global.mail.MailSender
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import java.util.concurrent.ConcurrentHashMap

class CapturingMailSender : MailSender(null) {
    val codes = ConcurrentHashMap<String, String>()

    override fun sendVerificationCode(email: String, code: String, purpose: String) {
        codes[email] = code
    }
}

@TestConfiguration
class TestMailConfig {

    @Bean
    @Primary
    fun capturingMailSender(): CapturingMailSender = CapturingMailSender()
}
