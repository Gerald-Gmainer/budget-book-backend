package com.gmainer.budgetbook.booking

import com.gmainer.budgetbook.AuthIntegration
import com.gmainer.budgetbook.booking.dto.BookingCreateRequest
import com.gmainer.budgetbook.testdata.TestUser
import com.gmainer.budgetbook.testhelper.toJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.math.BigDecimal
import java.time.LocalDate

class BookingControllerTest : AuthIntegration() {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should get all bookings`() {
        val auth = getUserAuthentication(TestUser.JoeMama)
        get("/bookings", auth.token).andExpect {
            status { isOk() }
            jsonPath("$.length()").value(10)
            jsonPath("$[0].name").value("Salary Payment")
            jsonPath("$[1].name").value("Grocery Shopping")
        }
    }

    @Test
    fun `should get booking by id`() {
        val auth = getUserAuthentication(TestUser.JoeMama)
        val bookingId = jdbcTemplate.queryForObject("SELECT id FROM bookings WHERE description = 'Work'", Long::class.java)

        get("/bookings/$bookingId", auth.token).andExpect {
            status { isOk() }
            jsonPath("$.description").value("Work")
        }
    }

    @Test
    fun `should return 404 for non-existent booking`() {
        val auth = getUserAuthentication(TestUser.JoeMama)
        get("/bookings/999", auth.token)
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should create a booking`() {
        val auth = getUserAuthentication(TestUser.JoeMama)
        val request = BookingCreateRequest(
            bookingDate = LocalDate.now(),
            description = "new booking",
            amount = BigDecimal("100.00"),
            categoryId = jdbcTemplate.queryForObject("SELECT id FROM categories WHERE name = 'Food'", Long::class.java)!!,
            accountId = jdbcTemplate.queryForObject("SELECT id FROM accounts WHERE name = 'Cash'", Long::class.java)!!
        )

        post("/bookings", request.toJson(), auth.token).andExpect {
            status { isCreated() }
            jsonPath("$.description").value("new booking")
        }
    }
}
