package com.lookation.view

import com.lookation.domain.AccountStatus
import com.lookation.repository.AccountRepository
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

/** JWT 인증 주체를 화면용 사용자 정보로 바꾼다. HTML 컨트롤러와 오류 화면이 같은 규칙을 쓴다. */
@Component
class ViewerLookup(
    private val accountRepository: AccountRepository,
) {

    fun resolve(authentication: Authentication?): Viewer? {
        val jwt = authentication?.principal as? Jwt ?: return null
        val accountId = jwt.subject?.toLongOrNull() ?: return null
        val account = accountRepository.findById(accountId) ?: return null
        if (account.status != AccountStatus.ACTIVE) return null
        return Viewer(
            id = account.id,
            nickname = account.nickname,
            roles = accountRepository.roles(account.id),
        )
    }
}
