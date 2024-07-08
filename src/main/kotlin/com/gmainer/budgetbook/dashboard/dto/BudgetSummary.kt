package com.gmainer.budgetbook.dashboard.dto

import java.math.BigDecimal

data class BudgetSummary(
    val filter: BudgetSummaryFilter,
    val income: BigDecimal,
    val outcome: BigDecimal,
    val balance: BigDecimal,
    val overviews: List<CategoryBookingOverview>
)
