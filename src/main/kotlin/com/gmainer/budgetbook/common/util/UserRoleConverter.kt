package com.gmainer.budgetbook.common.util

import com.gmainer.budgetbook.common.model.UserRole
import jakarta.persistence.Converter

@Converter(autoApply = true)
class UserRoleConverter : EnumConverter<UserRole>(UserRole::class.java)

