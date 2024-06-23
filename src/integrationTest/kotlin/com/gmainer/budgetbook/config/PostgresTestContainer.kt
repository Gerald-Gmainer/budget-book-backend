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
        val tenant1Container: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
            .withUsername("user1")
            .withPassword("password1")
            .withDatabaseName("tenant1")

        @JvmStatic
        @DynamicPropertySource
        fun setupProperties(registry: DynamicPropertyRegistry) {
            try {
                tenant1Container.start()
                val jdbcUrl = tenant1Container.jdbcUrl
                val mappedPort = tenant1Container.getMappedPort(5432)

                log.info("PostgreSQL container started at $jdbcUrl")
                log.info("PostgreSQL container mapped port $mappedPort")

                registry.add("tenants.tenant1.datasource.url") { tenant1Container.jdbcUrl }
                registry.add("tenants.tenant1.datasource.username") { tenant1Container.username }
                registry.add("tenants.tenant1.datasource.password") { tenant1Container.password }
            } catch (e: Exception) {
                log.error("Could not start PostgreSQL container: ${e.message}", e)
                throw RuntimeException("Could not start PostgreSQL container", e)
            }
        }
    }
}
