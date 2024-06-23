package com.gmainer.budgetbook.testhelper

import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation

data class KeycloakUser(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String,
    val roles: MutableList<String> = mutableListOf()
)

fun KeycloakUser.toRepresentation(): UserRepresentation {
    return UserRepresentation().apply {
        firstName = this@toRepresentation.firstName
        lastName = this@toRepresentation.lastName
        username = this@toRepresentation.username
        email = this@toRepresentation.email
        isEnabled = true
        isEmailVerified = true
        realmRoles = this@toRepresentation.roles
        credentials = listOf(
            CredentialRepresentation().apply {
                type = CredentialRepresentation.PASSWORD
                value = this@toRepresentation.password
                isTemporary = false
            }
        )
    }
}
