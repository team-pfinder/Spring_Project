package com.lookation.service.auth

import com.lookation.repository.auth.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RefreshTokenRevocationService(private val refreshTokenRepository: RefreshTokenRepository) {

    /**
     * 재사용 탐지 시 계열 폐기는 401 응답과 함께 커밋되어야 한다.
     * 바깥 트랜잭션이 예외로 롤백되므로 별도 트랜잭션에서 실행한다.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun revokeFamily(familyId: UUID) {
        refreshTokenRepository.revokeFamily(familyId)
    }
}
