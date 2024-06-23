package com.gmainer.budgetbook.common.util

import com.gmainer.budgetbook.common.model.IConvertableEnum
import jakarta.persistence.AttributeConverter

abstract class EnumConverter<T>(private val clazz: Class<T>) :
    AttributeConverter<T?, String?> where T : Enum<T>, T : IConvertableEnum {

    override fun convertToDatabaseColumn(status: T?): String? {
        return status?.code
    }

    override fun convertToEntityAttribute(code: String?): T? {
        return clazz.enumConstants.firstOrNull { it.code == code }
    }
}
