package com.gmainer.budgetbook.category.repository

import com.gmainer.budgetbook.category.model.CategoryColor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryColorRepository : JpaRepository<CategoryColor, Long> {
    fun findByName(name: String): CategoryColor?
}
