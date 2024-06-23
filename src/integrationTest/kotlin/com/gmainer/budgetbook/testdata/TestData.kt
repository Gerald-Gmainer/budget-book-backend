package com.gmainer.budgetbook.testdata

import com.gmainer.budgetbook.testhelper.KeycloakUser
import org.keycloak.representations.idm.RoleRepresentation

object TestUser {
    val JoeMama = KeycloakUser(
        firstName = "Joe",
        lastName = "Mama",
        email = "joe.mama@gmail.com",
        username = "joe.mama@gmail.com",
        password = "joe.mama420",
        roles = mutableListOf("admin")
    )
}

object TestRealmRoles {
    private const val CONTAINER_ID = "budgetbook-app"

    val AdminRole = RoleRepresentation().apply {
        id = "6a60e7c7-bd8f-45f8-8842-916eb7967b71"
        name = "admin"
        isComposite = true
        clientRole = false
        containerId = CONTAINER_ID
    }
}
