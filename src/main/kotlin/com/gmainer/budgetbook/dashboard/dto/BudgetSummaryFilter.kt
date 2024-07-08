package com.gmainer.budgetbook.dashboard.dto

import java.time.LocalDate

data class BudgetSummaryFilter(
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
    val accountId: Long? = null
)
