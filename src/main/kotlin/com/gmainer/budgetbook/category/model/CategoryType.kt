package com.gmainer.budgetbook.category.model

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class CategoryType {
    INCOME,
    OUTCOME
}

@Converter(autoApply = true)
class CategoryTypeConverter : AttributeConverter<CategoryType, String> {
    override fun convertToDatabaseColumn(attribute: CategoryType?): String {
        return attribute?.name?.lowercase() ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): CategoryType {
        return dbData?.uppercase()?.let { CategoryType.valueOf(it) } ?: CategoryType.OUTCOME
    }
}
