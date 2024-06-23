package com.gmainer.budgetbook.common.model

import com.fasterxml.jackson.annotation.JsonValue

interface IConvertableEnum {
    @get:JsonValue
    val code: String
}
