package com.gmainer.budgetbook.testdata.service

import com.gmainer.budgetbook.account.model.Account
import com.gmainer.budgetbook.account.repository.AccountRepository
import com.gmainer.budgetbook.booking.model.Booking
import com.gmainer.budgetbook.booking.repository.BookingRepository
import com.gmainer.budgetbook.category.repository.CategoryRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.random.Random

@Service
class TestDataBookingService @Autowired constructor(
    private val bookingRepository: BookingRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
) {

    @Transactional
    fun insertTestDataForMonth(yearMonth: String, accountName: String): String {
        val account = accountRepository.findByName(accountName) ?: throw IllegalArgumentException("Account not found")
        val bookings = mutableListOf<Booking>()

        bookings.add(createBooking("$yearMonth-10", BigDecimal("3175"), "Salary", account, "Objectbay"))

        if (Random.nextBoolean()) {
            bookings.add(createBooking("$yearMonth-10", BigDecimal("425.5"), "Child Benefit", account, "saved money"))
        }

        bookings.add(createBooking("$yearMonth-10", BigDecimal("800"), "Rent", account, "monthly rent"))
        bookings.add(createBooking("$yearMonth-11", BigDecimal("55.4"), "Home Services", account, "Generalli"))
        bookings.add(createBooking("$yearMonth-12", BigDecimal("94.21"), "Home Services", account, "power costs"))
        bookings.add(createBooking("$yearMonth-15", BigDecimal("112.12"), "Car", account, "maintenance"))

        repeat(3) {
            val day = randomDay()
            val amount = BigDecimal(Random.nextDouble(30.0, 50.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
            bookings.add(createBooking("$yearMonth-$day", amount, "Car", account, "gas"))
        }

        repeat(2) {
            val day = randomDay()
            val amount1 = BigDecimal(Random.nextDouble(5.0, 10.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
            val amount2 = BigDecimal(Random.nextDouble(6.0, 12.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
            bookings.add(createBooking("$yearMonth-$day", amount1, "Household", account, "diaper"))
            bookings.add(createBooking("$yearMonth-$day", amount2, "Household", account, "cloth"))
        }

        repeat(2) {
            val day = randomDay()
            val amount = BigDecimal(Random.nextDouble(40.0, 50.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
            bookings.add(createBooking("$yearMonth-$day", amount, "Entertainment", account, "cinema"))
        }

        repeat(2) {
            val day = randomDay()
            val amount = BigDecimal(Random.nextDouble(20.0, 30.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
            bookings.add(createBooking("$yearMonth-$day", amount, "Gift", account, "birthday"))
        }

        repeat(50) {
            val day = randomDay()
            val amount = BigDecimal(Random.nextDouble(1.0, 14.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
            bookings.add(createBooking("$yearMonth-$day", amount, "Groceries", account, "food"))
        }

        repeat(2) {
            val day = randomDay()
            val amount = BigDecimal(Random.nextDouble(25.0, 50.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
            bookings.add(createBooking("$yearMonth-$day", amount, "Eating Out", account, "restaurant"))
        }

        repeat(5) {
            val day = randomDay()
            val amount = BigDecimal(Random.nextDouble(2.0, 5.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
            bookings.add(createBooking("$yearMonth-$day", amount, "Other", account, "minor thing"))
        }

        bookingRepository.saveAll(bookings)

        return "Inserted ${bookings.size} rows into bookings table"
    }

    private fun createBooking(date: String, amount: BigDecimal, categoryName: String, account: Account, description: String): Booking {
        val category = categoryRepository.findByName(categoryName) ?: throw IllegalArgumentException("Category not found by name $categoryName")

        return Booking(
            bookingDate = LocalDate.parse(date),
            amount = amount,
            category = category,
            account = account,
            description = description
        )
    }

    private fun randomDay(): String {
        val day = (1..28).random()
        return day.toString().padStart(2, '0')
    }
}
