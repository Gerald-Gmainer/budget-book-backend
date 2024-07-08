package com.gmainer.budgetbook.booking.model

import com.gmainer.budgetbook.account.model.Account
import com.gmainer.budgetbook.booking.dto.BookingCreateRequest
import com.gmainer.budgetbook.booking.dto.BookingResponse
import com.gmainer.budgetbook.category.model.Category
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookings_id_seq")
    @SequenceGenerator(name = "bookings_id_seq", sequenceName = "bookings_id_seq", allocationSize = 1)
    var id: Long? = null,

    @Column(name = "booking_date", nullable = false)
    var bookingDate: LocalDate,

    @Column(nullable = false)
    var amount: BigDecimal,

    @Column(nullable = true)
    var description: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    val account: Account,

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)

fun Booking.toResponse(): BookingResponse {
    return BookingResponse(
        id = this.id!!,
        bookingDate = this.bookingDate,
        amount = this.amount,
        description = this.description,
        categoryId = this.category.id,
        categoryName = this.category.name,
        accountId = this.account.id,
        accountName = this.account.name,
    )
}

fun BookingCreateRequest.toEntity(category: Category, account: Account): Booking {
    return Booking(
        null,
        this.bookingDate,
        this.amount,
        this.description,
        category,
        account,
    )
}
