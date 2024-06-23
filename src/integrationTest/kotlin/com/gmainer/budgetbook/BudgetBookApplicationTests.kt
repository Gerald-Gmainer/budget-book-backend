package com.gmainer.budgetbook

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BudgetBookApplicationTests : AuthIntegration() {
    @Test
    fun contextLoads() {
        assert(true)
    }
}
