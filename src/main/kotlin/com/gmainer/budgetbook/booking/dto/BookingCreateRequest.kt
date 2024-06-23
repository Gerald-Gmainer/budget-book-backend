package com.gmainer.budgetbook.booking.dto

import java.math.BigDecimal
import java.time.LocalDate

data class BookingCreateRequest(
    val bookingDate: LocalDate,
    val amount: BigDecimal
)
