package com.gmainer.budgetbook

import com.gmainer.budgetbook.config.KeycloakAuthenticationService
import com.gmainer.budgetbook.testhelper.KeycloakTestAuthentication
import com.gmainer.budgetbook.testhelper.KeycloakUser
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired

class AuthIntegration : BaseIntegration() {

    @Suppress("VarCouldBeVal")
    @Autowired
    private lateinit var keycloakAuthenticationService: KeycloakAuthenticationService

    @AfterEach
    fun cleanupKeycloakUsers() {
        keycloakAuthenticationService.decomposeUserAuthentication()
    }

    fun getUserAuthentication(user: KeycloakUser): KeycloakTestAuthentication {
        return keycloakAuthenticationService.composeUserAuthentication(user)
    }
}
