package com.gmainer.budgetbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BudgetBookBackendApplication

fun main(args: Array<String>) {
    runApplication<BudgetBookBackendApplication>(*args)
}
