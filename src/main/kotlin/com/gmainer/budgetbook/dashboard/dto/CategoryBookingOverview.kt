package com.gmainer.budgetbook.dashboard.dto

import com.gmainer.budgetbook.booking.dto.BookingResponse
import com.gmainer.budgetbook.category.dto.CategoryResponse
import com.gmainer.budgetbook.category.model.CategoryType
import java.math.BigDecimal

data class CategoryBookingOverview(
    val category: CategoryResponse,
    val bookings: List<BookingResponse>,
    val categoryType: CategoryType,
    val amount: BigDecimal,
    val children: List<CategoryBookingOverview>
)
