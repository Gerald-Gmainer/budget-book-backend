package com.gmainer.budgetbook.account

import com.gmainer.budgetbook.AuthIntegration
import com.gmainer.budgetbook.testdata.TestUser
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
class AccountControllerTest : AuthIntegration() {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should get all accounts`() {
        val auth = getUserAuthentication(TestUser.JoeMama)

        get("/accounts", auth.token).andExpect {
            status { isOk() }
            jsonPath("$.length()").value(2)
            jsonPath("$[0].name").value("Cash")
            jsonPath("$[1].name").value("Debit Card")
        }
    }

    @Test
    fun `should get account by id`() {
        val auth = getUserAuthentication(TestUser.JoeMama)
        val cashId = jdbcTemplate.queryForObject("SELECT id FROM accounts WHERE name = 'Cash'", Long::class.java)

        get("/accounts/$cashId", auth.token).andExpect {
            status { isOk() }
            jsonPath("$.name").value("Cash")
            jsonPath("$.iconName").value("cash-multiple")
            jsonPath("$.colorCode").value("#33FF57")
        }

    }

    @Test
    fun `should return 404 for non-existent account`() {
        val auth = getUserAuthentication(TestUser.JoeMama)

        get("/accounts/999", auth.token)
            .andExpect { status { isNotFound() } }
    }
}
