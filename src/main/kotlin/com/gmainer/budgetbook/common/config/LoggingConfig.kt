package com.gmainer.budgetbook.common.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <R : Any> R.logger(): Lazy<Logger> {
    return lazy { LoggerFactory.getLogger(this.javaClass) }
}
