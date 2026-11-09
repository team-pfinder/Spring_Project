package com.lookation.service

import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.HostProfileRepository
import com.lookation.controller.form.ChargeRequestForm
import com.lookation.dto.ChargeDto
import com.lookation.controller.form.WithdrawalRequestForm
import com.lookation.dto.WithdrawalDto
import com.lookation.domain.ChargeStatus
import com.lookation.domain.WalletEntryType
import com.lookation.domain.WithdrawalStatus
import com.lookation.repository.ChargeRepository
import com.lookation.repository.WalletRepository
import com.lookation.repository.WithdrawalRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val chargeRepository: ChargeRepository,
    private val withdrawalRepository: WithdrawalRepository,
    private val hostProfileRepository: HostProfileRepository,
    private val sanctionService: com.lookation.service.SanctionService,
) {

    @Transactional
    fun requestCharge(accountId: Long, form: ChargeRequestForm): ChargeDto {
        sanctionService.checkWritable(accountId)
        val id = chargeRepository.insert(accountId, form.amount, form.depositorName)
        return requireNotNull(chargeRepository.findById(id)).toResponse()
    }

    @Transactional(readOnly = true)
    fun charges(accountId: Long): List<ChargeDto> =
        chargeRepository.findByAccount(accountId).map { it.toResponse() }

    @Transactional
    fun decideCharge(adminId: Long, chargeId: Long, approved: Boolean): ChargeDto {
        val charge = chargeRepository.findById(chargeId)
            ?: throw NotFoundException("충전 신청을 찾을 수 없습니다")
        val nextStatus = if (approved) ChargeStatus.APPROVED else ChargeStatus.REJECTED
        if (chargeRepository.updateStatus(chargeId, ChargeStatus.REQUESTED, nextStatus, adminId) != 1) {
            throw ConflictException("이미 처리된 충전 신청입니다")
        }
        if (approved) {
            walletRepository.ensureWallet(charge.accountId)
            walletRepository.deposit(charge.accountId, charge.amount)
            walletRepository.addEntry(charge.accountId, WalletEntryType.CHARGE, charge.amount, "CHARGE", chargeId)
        }
        return requireNotNull(chargeRepository.findById(chargeId)).toResponse()
    }

    @Transactional
    fun requestWithdrawal(accountId: Long, form: WithdrawalRequestForm): WithdrawalDto {
        sanctionService.checkWritable(accountId)
        val profile = hostProfileRepository.findByAccountId(accountId)
            ?: throw BadRequestException("호스트 프로필과 정산 계좌가 필요합니다")
        val bankName = profile.bankName ?: throw BadRequestException("정산 계좌가 등록되지 않았습니다")
        val accountNumberEnc = profile.bankAccountNumberEnc
            ?: throw BadRequestException("정산 계좌가 등록되지 않았습니다")
        val accountHolderEnc = profile.bankAccountHolderEnc
            ?: throw BadRequestException("정산 계좌가 등록되지 않았습니다")

        walletRepository.ensureWallet(accountId)
        if (!walletRepository.hold(accountId, form.amount)) {
            throw ConflictException("출금 가능한 잔액이 부족합니다")
        }
        val id = withdrawalRepository.insert(accountId, form.amount, bankName, accountNumberEnc, accountHolderEnc)
        walletRepository.addEntry(accountId, WalletEntryType.WITHDRAWAL, -form.amount, "WITHDRAWAL", id)
        return requireNotNull(withdrawalRepository.findById(id)).toResponse()
    }

    @Transactional(readOnly = true)
    fun withdrawals(accountId: Long): List<WithdrawalDto> =
        withdrawalRepository.findByAccount(accountId).map { it.toResponse() }

    @Transactional
    fun decideWithdrawal(adminId: Long, withdrawalId: Long, approved: Boolean): WithdrawalDto {
        val withdrawal = withdrawalRepository.findById(withdrawalId)
            ?: throw NotFoundException("출금 신청을 찾을 수 없습니다")
        val nextStatus = if (approved) WithdrawalStatus.PAID else WithdrawalStatus.REJECTED
        if (withdrawalRepository.updateStatus(withdrawalId, WithdrawalStatus.REQUESTED, nextStatus, adminId) != 1) {
            throw ConflictException("이미 처리된 출금 신청입니다")
        }
        if (!approved) {
            walletRepository.deposit(withdrawal.accountId, withdrawal.amount)
            walletRepository.addEntry(
                withdrawal.accountId,
                WalletEntryType.WITHDRAWAL,
                withdrawal.amount,
                "WITHDRAWAL",
                withdrawal.id,
            )
        }
        return requireNotNull(withdrawalRepository.findById(withdrawalId)).toResponse()
    }

    private fun com.lookation.repository.ChargeRow.toResponse() = ChargeDto(
        id = id,
        amount = amount,
        depositorName = depositorName,
        status = status,
    )

    private fun com.lookation.repository.WithdrawalRow.toResponse() = WithdrawalDto(
        id = id,
        amount = amount,
        status = status,
    )
}
