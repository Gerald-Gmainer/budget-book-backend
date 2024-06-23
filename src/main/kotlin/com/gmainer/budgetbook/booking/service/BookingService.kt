package com.gmainer.budgetbook.booking.service

import com.gmainer.budgetbook.booking.dto.BookingCreateRequest
import com.gmainer.budgetbook.booking.dto.BookingResponse
import com.gmainer.budgetbook.booking.model.Booking
import com.gmainer.budgetbook.booking.model.toEntity
import com.gmainer.budgetbook.booking.model.toResponse
import com.gmainer.budgetbook.booking.repository.BookingRepository
import org.springframework.stereotype.Service

@Service
class BookingService(private val bookingRepository: BookingRepository) {

    fun getAllBookings(): List<BookingResponse> {
        return bookingRepository.findAll().map { it.toResponse() }
    }

    fun createBooking(bookingDto: BookingCreateRequest): Booking {
        val booking = bookingDto.toEntity()
        return bookingRepository.save(booking)
    }
}
