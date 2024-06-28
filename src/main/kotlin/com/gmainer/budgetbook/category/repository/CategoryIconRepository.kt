package com.gmainer.budgetbook.category.repository

import com.gmainer.budgetbook.category.model.CategoryIcon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryIconRepository : JpaRepository<CategoryIcon, Long> {
    fun findByName(name: String): CategoryIcon?
}
