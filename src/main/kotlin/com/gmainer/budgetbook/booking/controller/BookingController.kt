package com.gmainer.budgetbook.booking.controller

import com.gmainer.budgetbook.booking.dto.BookingCreateRequest
import com.gmainer.budgetbook.booking.dto.BookingResponse
import com.gmainer.budgetbook.booking.service.BookingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/bookings")
class BookingController(private val bookingService: BookingService) {

    @GetMapping
    fun getAllBookings(): ResponseEntity<List<BookingResponse>> {
        return ResponseEntity.ok(bookingService.getAllBookings())
    }

    @GetMapping("/{id}")
    fun getBookingById(@PathVariable id: Long): ResponseEntity<BookingResponse> {
        val booking = bookingService.findById(id)
        return ResponseEntity.ok(booking)
    }

    @PostMapping
    fun createBooking(@RequestBody bookingDto: BookingCreateRequest): ResponseEntity<BookingResponse> {
        val response = bookingService.createBooking(bookingDto)
        return ResponseEntity.created(URI.create("/bookings/${response.id}")).body(response)
    }
}
