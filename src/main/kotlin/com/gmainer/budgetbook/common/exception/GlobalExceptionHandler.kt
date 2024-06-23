package com.gmainer.budgetbook.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
