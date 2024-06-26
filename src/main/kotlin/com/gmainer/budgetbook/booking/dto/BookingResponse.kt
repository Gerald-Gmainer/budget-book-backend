package com.gmainer.budgetbook.booking.dto

import java.math.BigDecimal
import java.time.LocalDate

data class BookingResponse(
    val id: Long = 0,
    val bookingDate: LocalDate,
    val amount: BigDecimal,
)
