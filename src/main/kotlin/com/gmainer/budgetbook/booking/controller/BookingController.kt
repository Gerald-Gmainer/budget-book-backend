package com.gmainer.budgetbook.booking.controller

import com.gmainer.budgetbook.booking.dto.BookingCreateRequest
import com.gmainer.budgetbook.booking.dto.BookingResponse
import com.gmainer.budgetbook.booking.model.Booking
import com.gmainer.budgetbook.booking.service.BookingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookings")
class BookingController(private val bookingService: BookingService) {

    @GetMapping
    fun getAllBookings(): List<BookingResponse> {
        return bookingService.getAllBookings()
    }

    @PostMapping
    fun createBooking(@RequestBody bookingDto: BookingCreateRequest): Booking {
        return bookingService.createBooking(bookingDto)
    }
}
