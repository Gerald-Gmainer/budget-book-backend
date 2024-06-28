package com.gmainer.budgetbook.testdata.controller

import com.gmainer.budgetbook.testdata.service.TestDataBookingService
import com.gmainer.budgetbook.testdata.service.TestDataCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/insertTestData")
class TestDataController @Autowired constructor(
    private val testDataBooking: TestDataBookingService,
    private val testDataCategoryService: TestDataCategoryService
) {

    @PostMapping("/category")
    fun insertCategoryTestData(): ResponseEntity<String> {
        return try {
            val result = testDataCategoryService.insertTestCategories()
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Error: ${e.message}")
        }
    }

    @PostMapping("/month")
    fun insertTestDataForMonth(
        @RequestParam month: String,
        @RequestParam accountName: String
    ): ResponseEntity<String> {
        return try {
            val result = testDataBooking.insertTestDataForMonth(month, accountName)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Error: ${e.message}")
        }
    }
}
