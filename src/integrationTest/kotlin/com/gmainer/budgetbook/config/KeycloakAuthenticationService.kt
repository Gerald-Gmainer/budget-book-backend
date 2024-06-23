package com.gmainer.budgetbook.config

import com.gmainer.budgetbook.testdata.TestRealmRoles
import com.gmainer.budgetbook.testhelper.KeycloakTestAuthentication
import com.gmainer.budgetbook.testhelper.KeycloakUser
import com.gmainer.budgetbook.testhelper.toRepresentation
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.opentest4j.TestAbortedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KeycloakAuthenticationService {

    @Suppress("VarCouldBeVal")
    @Value("\${keycloak.auth-server-url}")
    private lateinit var serverUrl: String

    @Suppress("VarCouldBeVal")
    @Value("\${keycloak.realm}")
    private lateinit var realm: String

    @Suppress("VarCouldBeVal")
    @Value("\${keycloak.resource}")
    private lateinit var clientId: String

    @Suppress("VarCouldBeVal")
    @Value("\${keycloak.credentials.secret}")
    private lateinit var clientSecret: String

    @Autowired
    lateinit var keycloak: Keycloak

    fun composeUserAuthentication(user: KeycloakUser): KeycloakTestAuthentication {
        keycloak.realm(realm).users().create(user.toRepresentation())

        val createdUser = keycloak.realm(realm).users().search(user.username, true)
            .firstOrNull()
            ?: throw TestAbortedException("Failed to retrieve created user")

        keycloak.realm(realm).users().get(createdUser.id).roles().realmLevel().add(
            mutableListOf(TestRealmRoles.AdminRole)
        )

        val tokenManager = KeycloakBuilder.builder()
            .realm(realm)
            .serverUrl(serverUrl)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .username(createdUser.username)
            .password(user.password)
            .build()
            .tokenManager()

        return KeycloakTestAuthentication(
            user = createdUser,
            token = tokenManager.accessTokenString
        )
    }

    fun decomposeUserAuthentication() {
        keycloak.realm(realm).users().list().forEach { user ->
            keycloak.realm(realm).users().delete(user.id)
        }
    }
}
