package com.gmainer.budgetbook

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.gmainer.budgetbook.config.KeycloakTestContainer
import com.gmainer.budgetbook.config.PostgresTestContainer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.test.web.servlet.*
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@SpringBootTest
@EnableConfigurationProperties
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BaseIntegration : KeycloakTestContainer, PostgresTestContainer {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var wireMockServer: WireMockServer

    @Autowired
    lateinit var dataSource: DataSource

    @BeforeAll
    fun setup() {
        WireMock.configureFor("localhost", wireMockServer.port())
    }

    @BeforeEach
    fun setupDb() {
        val databasePopulator = ResourceDatabasePopulator()
        databasePopulator.addScript(ClassPathResource("sql/setup.sql"))
        databasePopulator.execute(dataSource)
    }

    @AfterEach
    fun cleanup() {
        wireMockServer.resetMappings()

        val databasePopulator = ResourceDatabasePopulator()
        databasePopulator.addScript(ClassPathResource("sql/cleanup.sql"))
        databasePopulator.execute(dataSource)
    }

    fun get(path: String, token: String): ResultActionsDsl {
        return mockMvc.get(path) {
            token.let {
                authHeader(it)
            }
        }
    }

    fun put(path: String, jsonContent: String? = null, token: String): ResultActionsDsl {
        return mockMvc.put(path) {
            authHeader(token)
            content = jsonContent
            contentType = MediaType.APPLICATION_JSON
        }
    }

    fun post(
        path: String,
        jsonContent: String? = null,
        token: String? = null
    ): ResultActionsDsl {
        return mockMvc.post(path) {
            authHeader(token)
            content = jsonContent
            contentType = MediaType.APPLICATION_JSON
        }
    }

    fun delete(path: String, token: String): ResultActionsDsl {
        return mockMvc.delete(path) {
            authHeader(token)
            contentType = MediaType.APPLICATION_JSON
        }
    }

    private fun MockHttpServletRequestDsl.authHeader(token: String?) {
        token?.let {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
    }
}
