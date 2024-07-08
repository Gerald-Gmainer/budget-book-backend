package com.gmainer.budgetbook.dashboard.controller

import com.gmainer.budgetbook.dashboard.dto.BudgetSummary
import com.gmainer.budgetbook.dashboard.dto.BudgetSummaryFilter
import com.gmainer.budgetbook.dashboard.service.BudgetSummaryService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/dashboard")
class DashboardController(private val summaryService: BudgetSummaryService) {

    // With account ID filter: /dashboard/summary/20240501/20240530?accountId=1
    // Without account ID filter: /dashboard/summary/20240501/20240530
    @GetMapping("summary/{dateFrom}/{dateTo}")
    fun getBudgetSummary(
        @PathVariable @DateTimeFormat(pattern = "yyyyMMdd") dateFrom: LocalDate,
        @PathVariable @DateTimeFormat(pattern = "yyyyMMdd") dateTo: LocalDate,
        @RequestParam(required = false) accountId: Long?
    ): ResponseEntity<BudgetSummary> {
        val filter = BudgetSummaryFilter(dateFrom, dateTo, accountId)
        val summary = summaryService.determineBudgetSummary(filter);
        println(summary.toString())
        return ResponseEntity.ok(summary)
    }
}

