package com.gmainer.budgetbook.testhelper

import org.keycloak.representations.idm.UserRepresentation

data class KeycloakTestAuthentication(
    val user: UserRepresentation,
    val token: String
)
