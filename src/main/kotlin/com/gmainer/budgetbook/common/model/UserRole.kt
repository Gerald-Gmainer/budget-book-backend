package com.gmainer.budgetbook.common.model

enum class UserRole(override val code: String) : IConvertableEnum {
    OWNER("owner"),
    ADMIN("admin"),
    PURCHASING("purchasing"),
    USER("user"),
    MANAGER("manager"),
    ENTERPRISE_MANAGER("enterprise_manager"),
    USER_MANAGER("user_manager"),
    TENANT_ADMIN("tenant_admin")
}
