package com.gmainer.budgetbook.account.dto

data class AccountResponse(
    val id: Long,
    val name: String,
    val iconName: String,
    val iconOrder: Int,
    val colorCode: String,
    val colorOrder: Int
)
