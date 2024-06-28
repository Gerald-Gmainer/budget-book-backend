package com.gmainer.budgetbook.account.repository

import com.gmainer.budgetbook.account.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {

    fun findByName(accountName: String): Account?
}
