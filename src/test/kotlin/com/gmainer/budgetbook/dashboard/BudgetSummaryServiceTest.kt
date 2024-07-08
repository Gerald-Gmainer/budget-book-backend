package com.gmainer.budgetbook.dashboard

import com.gmainer.budgetbook.account.model.Account
import com.gmainer.budgetbook.account.model.AccountColor
import com.gmainer.budgetbook.account.model.AccountIcon
import com.gmainer.budgetbook.booking.model.Booking
import com.gmainer.budgetbook.booking.repository.BookingRepository
import com.gmainer.budgetbook.category.model.Category
import com.gmainer.budgetbook.category.model.CategoryColor
import com.gmainer.budgetbook.category.model.CategoryIcon
import com.gmainer.budgetbook.category.model.CategoryType
import com.gmainer.budgetbook.dashboard.dto.BudgetSummaryFilter
import com.gmainer.budgetbook.dashboard.service.BudgetSummaryService
import com.gmainer.budgetbook.testhelper.MockitoHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
class BudgetSummaryServiceTest {
    private val bookingRepository = Mockito.mock(BookingRepository::class.java)
    private val budgetSummaryService = BudgetSummaryService(bookingRepository)

    private val filterDateStart = LocalDate.of(2024, 5, 1)
    private val filterDateEnd = LocalDate.of(2024, 5, 30)
    private final val accountIcon = AccountIcon(id = 1L, name = "icon", uiOrder = 1)
    private final val accountColor = AccountColor(id = 1L, name = "Cash", code = "#000000", uiOrder = 1)
    private final val categoryIcon = CategoryIcon(id = 1L, name = "icon", uiOrder = 1)
    private final val categoryColor = CategoryColor(id = 1L, code = "#FF0000")

    val account = Account(id = 1L, name = "Checking", icon = accountIcon, color = accountColor)
    val foodCategory = Category(id = 1L, name = "Food", icon = categoryIcon, color = categoryColor, categoryType = CategoryType.OUTCOME)
    val salaryCategory = Category(id = 1L, name = "Salary", icon = categoryIcon, color = categoryColor, categoryType = CategoryType.INCOME)

    @Test
    fun `should calculate empty bookings correctly`() {
        Mockito.`when`(bookingRepository.findByBookingDateBetween(MockitoHelper.anyObject(), MockitoHelper.anyObject())).thenReturn(listOf())

        val filter = BudgetSummaryFilter(filterDateStart, filterDateEnd)
        val summary = budgetSummaryService.determineBudgetSummary(filter)

        assertEquals(BigDecimal(0), summary.income)
        assertEquals(BigDecimal(0), summary.outcome)
        assertEquals(BigDecimal(0), summary.balance)
        assertEquals(0, summary.overviews.size)
    }

    @Test
    fun `should calculate income and outcome correctly`() {
        val bookings = listOf(
            Booking(id = 1L, bookingDate = LocalDate.of(2024, 5, 1), amount = BigDecimal(100), description = "Work", category = salaryCategory, account = account),
            Booking(id = 2L, bookingDate = LocalDate.of(2024, 5, 2), amount = BigDecimal(24.5), description = "Food 1", category = foodCategory, account = account),
            Booking(id = 3L, bookingDate = LocalDate.of(2024, 5, 3), amount = BigDecimal(2), description = "Food 2", category = foodCategory, account = account)
        )

        Mockito.`when`(bookingRepository.findByBookingDateBetween(MockitoHelper.anyObject(), MockitoHelper.anyObject())).thenReturn(bookings)

        val filter = BudgetSummaryFilter(filterDateStart, filterDateEnd)
        val summary = budgetSummaryService.determineBudgetSummary(filter)

        assertEquals(BigDecimal(100), summary.income)
        assertEquals(BigDecimal(26.5), summary.outcome)
        assertEquals(BigDecimal(73.5), summary.balance)
        assertEquals(2, summary.overviews.size)
        assertEquals("Salary", summary.overviews[0].category.name)
        assertEquals("Food", summary.overviews[1].category.name)
    }

    @Test
    fun `should calculate with account ID`() {
        val bookings = listOf(
            Booking(id = 1L, bookingDate = LocalDate.of(2024, 5, 1), amount = BigDecimal(100), description = "Work", category = salaryCategory, account = account),
            Booking(id = 2L, bookingDate = LocalDate.of(2024, 5, 2), amount = BigDecimal(24.5), description = "Food 1", category = foodCategory, account = account),
            Booking(id = 3L, bookingDate = LocalDate.of(2024, 5, 3), amount = BigDecimal(2), description = "Food 2", category = foodCategory, account = account)
        )

        Mockito.`when`(bookingRepository.findByBookingDateBetweenAndAccountId(MockitoHelper.anyObject(), MockitoHelper.anyObject(), MockitoHelper.anyLong())).thenReturn(bookings)

        val filter = BudgetSummaryFilter(filterDateStart, filterDateEnd, 1L)
        val summary = budgetSummaryService.determineBudgetSummary(filter)

        assertEquals(BigDecimal(100), summary.income)
        assertEquals(BigDecimal(26.5), summary.outcome)
        assertEquals(BigDecimal(73.5), summary.balance)
        assertEquals(2, summary.overviews.size)
        assertEquals("Salary", summary.overviews[0].category.name)
        assertEquals("Food", summary.overviews[1].category.name)

    }

    @Test
    fun `should group parent categories correctly`() {
        val groceriesCategory = Category(id = 4L, name = "Groceries", icon = categoryIcon, color = categoryColor, categoryType = CategoryType.OUTCOME, parent = foodCategory)

        val bookings = listOf(
            Booking(id = 1L, bookingDate = LocalDate.of(2024, 5, 1), amount = BigDecimal(100), description = "Work", category = salaryCategory, account = account),
            Booking(id = 2L, bookingDate = LocalDate.of(2024, 5, 2), amount = BigDecimal(5), description = "Food 1", category = foodCategory, account = account),
            Booking(id = 3L, bookingDate = LocalDate.of(2024, 5, 3), amount = BigDecimal(10), description = "Food 2", category = groceriesCategory, account = account),
            Booking(id = 3L, bookingDate = LocalDate.of(2024, 5, 3), amount = BigDecimal(15), description = "Food 3", category = groceriesCategory, account = account)
        )

        Mockito.`when`(bookingRepository.findByBookingDateBetweenAndAccountId(MockitoHelper.anyObject(), MockitoHelper.anyObject(), MockitoHelper.anyLong())).thenReturn(bookings)

        val filter = BudgetSummaryFilter(filterDateStart, filterDateEnd, 1L)
        val summary = budgetSummaryService.determineBudgetSummary(filter)

        assertEquals(BigDecimal(100), summary.income)
        assertEquals(BigDecimal(30), summary.outcome)
        assertEquals(BigDecimal(70), summary.balance)
        assertEquals(2, summary.overviews.size)
        assertEquals("Salary", summary.overviews[0].category.name)
        assertEquals("Food", summary.overviews[1].category.name)
        assertEquals("Food 1", summary.overviews[1].bookings[0].description)

        assertEquals(1, summary.overviews[1].children.size)
        assertEquals("Groceries", summary.overviews[1].children[0].category.name)
        assertEquals("Food 2", summary.overviews[1].children[0].bookings[0].description)
        assertEquals("Food 3", summary.overviews[1].children[0].bookings[1].description)
    }

    @Test
    fun `should group children without parent categories correctly`() {
        val groceriesCategory = Category(id = 4L, name = "Groceries", icon = categoryIcon, color = categoryColor, categoryType = CategoryType.OUTCOME, parent = foodCategory)

        val bookings = listOf(
            Booking(id = 1L, bookingDate = LocalDate.of(2024, 5, 1), amount = BigDecimal(100), description = "Work", category = salaryCategory, account = account),
            Booking(id = 3L, bookingDate = LocalDate.of(2024, 5, 3), amount = BigDecimal(10), description = "Food 2", category = groceriesCategory, account = account),
            Booking(id = 3L, bookingDate = LocalDate.of(2024, 5, 3), amount = BigDecimal(15), description = "Food 3", category = groceriesCategory, account = account)
        )

        Mockito.`when`(bookingRepository.findByBookingDateBetweenAndAccountId(MockitoHelper.anyObject(), MockitoHelper.anyObject(), MockitoHelper.anyLong())).thenReturn(bookings)

        val filter = BudgetSummaryFilter(filterDateStart, filterDateEnd, 1L)
        val summary = budgetSummaryService.determineBudgetSummary(filter)

        assertEquals(BigDecimal(100), summary.income)
        assertEquals(BigDecimal(25), summary.outcome)
        assertEquals(BigDecimal(75), summary.balance)
        assertEquals(2, summary.overviews.size)
        assertEquals("Salary", summary.overviews[0].category.name)
        assertEquals("Food", summary.overviews[1].category.name)

        assertEquals(1, summary.overviews[1].children.size)
        assertEquals("Groceries", summary.overviews[1].children[0].category.name)
        assertEquals("Food 2", summary.overviews[1].children[0].bookings[0].description)
        assertEquals("Food 3", summary.overviews[1].children[0].bookings[1].description)
    }
}
