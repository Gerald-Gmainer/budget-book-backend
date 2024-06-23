package com.gmainer.budgetbook.booking.model

import com.gmainer.budgetbook.booking.dto.BookingCreateRequest
import com.gmainer.budgetbook.booking.dto.BookingResponse
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "booking_date", nullable = false)
    var bookingDate: LocalDate? = null,

    @Column(nullable = false)
    var amount: BigDecimal? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null
)

fun Booking.toResponse(): BookingResponse {
    return BookingResponse(
        id = this.id!!,
        bookingDate = this.bookingDate!!,
        amount = this.amount!!
    )
}

fun BookingCreateRequest.toEntity(): Booking {
    return Booking(
        null,
        this.bookingDate,
        this.amount
    )
}
