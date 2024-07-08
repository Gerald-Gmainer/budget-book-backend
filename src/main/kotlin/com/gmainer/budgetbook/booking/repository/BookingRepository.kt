package com.gmainer.budgetbook.booking.repository

import com.gmainer.budgetbook.booking.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface BookingRepository : JpaRepository<Booking, Long> {
    fun findByBookingDateBetween(dateFrom: LocalDate, dateTo: LocalDate): List<Booking>
    fun findByBookingDateBetweenAndAccountId(dateFrom: LocalDate, dateTo: LocalDate, accountId: Long): List<Booking>
}
