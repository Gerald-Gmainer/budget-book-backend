package com.gmainer.budgetbook.config

import com.gmainer.budgetbook.common.config.logger
import dasniko.testcontainers.keycloak.KeycloakContainer
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.lifecycle.Startables
import java.util.concurrent.ExecutionException

@Testcontainers
interface KeycloakTestContainer {
    companion object {
        private val log by logger()

        private const val ENV_DISABLE_KEYCLOAK_TEST_CONTAINER = "DISABLE_KEYCLOAK_TEST_CONTAINER"

        private const val DOCKER_IMAGE = "quay.io/keycloak/keycloak:24.0.4"
        private const val EXPOSED_PORT = 8080
        private const val EXPOSED_ADMIN_CONSOLE_PORT = 9990

        @Container
        private val keycloakContainer: KeycloakContainer = KeycloakContainer(DOCKER_IMAGE)
            .withRealmImportFile("keycloak/realm.json")
            .withExposedPorts(EXPOSED_PORT, EXPOSED_ADMIN_CONSOLE_PORT)

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            if (keycloakTestcontainerDisabled()) {
                log.info("Keycloak test container is disabled")
                return
            }

            try {
                Startables.deepStart(keycloakContainer).get()
                log.info("Keycloak container started at ${keycloakContainer.authServerUrl}")
            } catch (e: ExecutionException) {
                log.error("Could not start Keycloak container: ${e.message}", e)
                throw RuntimeException("Could not start Keycloak container", e)
            } catch (e: Exception) {
                log.error("Unexpected error occurred: ${e.message}", e)
                throw RuntimeException("Unexpected error occurred while starting Keycloak container", e)
            }

            registry.add("keycloak.realm") { "genoa" }
            registry.add("keycloak.resource") { "seqify-app" }
            registry.add("keycloak.credentials.secret") { "secret" }
            registry.add("keycloak.auth-server-url") { keycloakContainer.authServerUrl }
        }

        private fun keycloakTestcontainerDisabled() =
            System.getenv(ENV_DISABLE_KEYCLOAK_TEST_CONTAINER)
                .orEmpty()
                .equals("true", true)
    }
}
