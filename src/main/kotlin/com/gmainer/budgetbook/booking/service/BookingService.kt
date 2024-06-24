package com.gmainer.budgetbook.booking.service

import com.gmainer.budgetbook.account.repository.AccountRepository
import com.gmainer.budgetbook.booking.dto.BookingCreateRequest
import com.gmainer.budgetbook.booking.dto.BookingResponse
import com.gmainer.budgetbook.booking.model.toEntity
import com.gmainer.budgetbook.booking.model.toResponse
import com.gmainer.budgetbook.booking.repository.BookingRepository
import com.gmainer.budgetbook.category.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository
) {

    fun getAllBookings(): List<BookingResponse> {
        return bookingRepository.findAll().map { it.toResponse() }
    }

    fun findById(id: Long): BookingResponse {
        val booking = bookingRepository.findById(id).orElseThrow { NoSuchElementException("Booking not found with id $id") }
        return booking.toResponse()
    }

    fun createBooking(bookingDto: BookingCreateRequest): BookingResponse {
        val category = categoryRepository.findById(bookingDto.categoryId)
            .orElseThrow { IllegalArgumentException("Category not found") }
        val account = accountRepository.findById(bookingDto.accountId)
            .orElseThrow { IllegalArgumentException("Account not found") }
        val booking = bookingDto.toEntity(category, account)
        return bookingRepository.save(booking).toResponse()
    }
}
