package com.lookation.global.mail

interface MailSender {
    fun sendVerificationCode(email: String, code: String, purpose: String)
}
