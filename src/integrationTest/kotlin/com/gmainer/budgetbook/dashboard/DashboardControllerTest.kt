package com.gmainer.budgetbook.dashboard

import com.gmainer.budgetbook.BaseIntegration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath


@SpringBootTest
class DashboardControllerTest : BaseIntegration() {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should load summary correctly`() {
        println("----------------------- OMG -------------------------------")
        //val auth = getUserAuthentication(TestUser.JoeMama)
        val resultActions = get("/dashboard/summary/2024-06-01/2024-06-30", "asdf")
            //.andDo(MockMvcResultHandlers.print())
            //.andDo { dsl -> println(dsl) }
            //.andExpect(status().isOk)
            .andExpect {
                status { isOk() }
                jsonPath("$.income").value(2725.5)
                jsonPath("$.outcome").value(1261.5)
                jsonPath("$.balance").value(1464)
                //jsonPath("$.overviews.length()").value(5)
                jsonPath("$.overviews.length()").value(333333)      // ??
            }
        //val result: MvcResult = resultActions.andReturn()
        //val contentAsString = result.response.contentAsString
        //println(contentAsString)
        println("----------------------- ASDF -------------------------------")
    }
}
