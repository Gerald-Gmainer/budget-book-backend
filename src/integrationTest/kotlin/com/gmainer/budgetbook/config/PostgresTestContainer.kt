package com.gmainer.budgetbook.config

import com.gmainer.budgetbook.common.config.logger
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
interface PostgresTestContainer {
    companion object {
        private val log by logger()

        @Container
        val testContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
            .withUsername("user1")
            .withPassword("password1")
            .withDatabaseName("tenant1")

        @JvmStatic
        @DynamicPropertySource
        fun setupProperties(registry: DynamicPropertyRegistry) {
            try {
                testContainer.start()

                registry.add("spring.datasource.url") { testContainer.jdbcUrl }
                registry.add("spring.datasource.username") { testContainer.username }
                registry.add("spring.datasource.password") { testContainer.password }
            } catch (e: Exception) {
                log.error("Could not start PostgreSQL container: ${e.message}", e)
                throw RuntimeException("Could not start PostgreSQL container", e)
            }
        }
    }
}
