package com.gmainer.budgetbook.category.repository

import com.gmainer.budgetbook.category.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long>
