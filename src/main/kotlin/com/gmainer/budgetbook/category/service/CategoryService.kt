package com.gmainer.budgetbook.category.service

import com.gmainer.budgetbook.category.dto.CategoryResponse
import com.gmainer.budgetbook.category.model.toResponse
import com.gmainer.budgetbook.category.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun findAll(): List<CategoryResponse> {
        return categoryRepository.findAll().map { it.toResponse() }
    }

    fun findById(id: Long): CategoryResponse {
        val category = categoryRepository.findById(id).orElseThrow { NoSuchElementException("Category not found with id $id") }
        return category.toResponse()
    }

}
