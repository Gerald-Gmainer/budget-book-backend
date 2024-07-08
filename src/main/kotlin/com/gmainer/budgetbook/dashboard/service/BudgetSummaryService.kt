package com.gmainer.budgetbook.dashboard.service

import com.gmainer.budgetbook.booking.model.Booking
import com.gmainer.budgetbook.booking.model.toResponse
import com.gmainer.budgetbook.booking.repository.BookingRepository
import com.gmainer.budgetbook.category.model.Category
import com.gmainer.budgetbook.category.model.CategoryType
import com.gmainer.budgetbook.category.model.toResponse
import com.gmainer.budgetbook.common.config.logger
import com.gmainer.budgetbook.dashboard.dto.BudgetSummary
import com.gmainer.budgetbook.dashboard.dto.BudgetSummaryFilter
import com.gmainer.budgetbook.dashboard.dto.CategoryBookingOverview
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BudgetSummaryService(private val bookingRepository: BookingRepository) {
    private val log by logger()

    fun determineBudgetSummary(filter: BudgetSummaryFilter): BudgetSummary {
        val bookings = fetchBookings(filter)
        val overviews = determineOverviews(bookings)
        val income = bookings.filter { it.category.categoryType == CategoryType.INCOME }.sumOf { it.amount }
        val outcome = bookings.filter { it.category.categoryType == CategoryType.OUTCOME }.sumOf { it.amount }
        val balance = income - outcome

        return BudgetSummary(
            filter = filter,
            income = income,
            outcome = outcome,
            balance = balance,
            overviews = overviews
        )
    }

    private fun fetchBookings(filter: BudgetSummaryFilter): List<Booking> {
        return if (filter.accountId != null) {
            bookingRepository.findByBookingDateBetweenAndAccountId(
                filter.dateFrom,
                filter.dateTo,
                filter.accountId
            )
        } else {
            bookingRepository.findByBookingDateBetween(
                filter.dateFrom,
                filter.dateTo
            )
        }
    }

    private fun determineOverviews(bookings: List<Booking>): List<CategoryBookingOverview> {
        val allCategories = bookings.map { it.category }.flatMap { listOf(it) + getParentCategories(it) }.distinct()
        val rootCategories = allCategories.filter { it.parent == null }
        val overviews = rootCategories.map { rootCategory ->
            buildCategoryOverview(rootCategory, bookings)
        }

        return overviews.filter { it.amount > BigDecimal.ZERO || it.children.isNotEmpty() }
    }

    private fun getParentCategories(category: Category): List<Category> {
        val parents = mutableListOf<Category>()
        var currentCategory: Category? = category.parent
        while (currentCategory != null) {
            parents.add(currentCategory)
            currentCategory = currentCategory.parent
        }
        return parents
    }

    private fun buildCategoryOverview(category: Category, allBookings: List<Booking>): CategoryBookingOverview {
        val directBookings = allBookings.filter { it.category == category }
        val childCategories = allBookings.map { it.category }.filter { it.parent == category }.distinct()

        val childOverviews = childCategories.map { childCategory ->
            buildCategoryOverview(childCategory, allBookings)
        }

        val totalAmount = directBookings.sumOf { it.amount } + childOverviews.sumOf { it.amount }

        return CategoryBookingOverview(
            category = category.toResponse(),
            categoryType = category.categoryType,
            bookings = directBookings.map { it.toResponse() },
            amount = totalAmount,
            children = childOverviews
        )
    }
}
