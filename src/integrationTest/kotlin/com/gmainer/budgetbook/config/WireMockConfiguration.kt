package com.gmainer.budgetbook.config

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val WIREMOCK_PORT = 8090

@Configuration
class WireMockConfiguration {
    @Bean(initMethod = "start", destroyMethod = "stop")
    fun wireMockServer(): WireMockServer {
        return WireMockServer(WIREMOCK_PORT)
    }
}
