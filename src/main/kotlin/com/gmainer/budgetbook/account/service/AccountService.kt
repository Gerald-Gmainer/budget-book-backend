package com.gmainer.budgetbook.account.service

import com.gmainer.budgetbook.account.dto.AccountResponse
import com.gmainer.budgetbook.account.model.toResponse
import com.gmainer.budgetbook.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun findAll(): List<AccountResponse> {
        return accountRepository.findAll().map { it.toResponse() }
    }

    fun findById(id: Long): AccountResponse {
        val account = accountRepository.findById(id).orElseThrow { NoSuchElementException("Account not found with id $id") }
        return account.toResponse()
    }
}
