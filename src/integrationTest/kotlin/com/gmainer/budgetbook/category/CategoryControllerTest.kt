package com.gmainer.budgetbook.category

import com.gmainer.budgetbook.AuthIntegration
import com.gmainer.budgetbook.testdata.TestUser
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

class CategoryControllerTest : AuthIntegration() {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should get all categories`() {
        val auth = getUserAuthentication(TestUser.JoeMama)
        get("/categories", auth.token).andExpect {
            status { isOk() }
            jsonPath("$.length()").value(16)
            jsonPath("$[0].name").value("work")
            jsonPath("$[1].name").value("house")
            jsonPath("$[2].name").value("car")
            jsonPath("$[3].name").value("food")
            jsonPath("$[4].name").value("baby")
            jsonPath("$[5].name").value("entertainment")
            jsonPath("$[6].name").value("eating out")
            jsonPath("$[7].name").value("other")
            jsonPath("$[8].name").value("Daiku")
            jsonPath("$[8].parentName").value("Personal")
            jsonPath("$[9].name").value("Sport")
            jsonPath("$[9].parentName").value("Personal")
            jsonPath("$[10].name").value("Investment")
            jsonPath("$[10].parentName").value("Personal")
            jsonPath("$[11].name").value("Hygiene")
            jsonPath("$[11].parentName").value("Personal")
            jsonPath("$[12].name").value("Cloth")
            jsonPath("$[12].parentName").value("Personal")
            jsonPath("$[13].name").value("Car")
            jsonPath("$[13].parentName").value("Transport")
            jsonPath("$[14].name").value("Public Transit")
            jsonPath("$[14].parentName").value("Transport")
        }
    }

    @Test
    fun `should get category by id`() {
        val auth = getUserAuthentication(TestUser.JoeMama)
        val id = jdbcTemplate.queryForObject("SELECT id FROM categories WHERE name = 'Groceries'", Long::class.java)

        get("/categories/$id", auth.token).andExpect {
            status { isOk() }
            jsonPath("$.name").value("Groceries")
            jsonPath("$.iconName").value("food")
            jsonPath("$.colorCode").value("#FF6633")
            jsonPath("$.type").value("OUTCOME")
            jsonPath("$.parentName").value("Food")
        }
    }

    @Test
    fun `should return 404 for non-existent category`() {
        val auth = getUserAuthentication(TestUser.JoeMama)
        get("/categories/999", auth.token)
            .andExpect { status { isNotFound() } }
    }
}
