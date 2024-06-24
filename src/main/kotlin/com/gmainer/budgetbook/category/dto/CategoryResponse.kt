package com.gmainer.budgetbook.category.dto

import com.gmainer.budgetbook.category.model.CategoryType

data class CategoryResponse(
    val id: Long,
    val name: String,
    val iconName: String,
    val colorCode: String,
    val type: CategoryType,
    val parentId: Long? = null,
    val parentName: String? = null
)
