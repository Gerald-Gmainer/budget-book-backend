package com.gmainer.budgetbook.booking.repository

import com.gmainer.budgetbook.booking.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository : JpaRepository<Booking, Long>
