package com.gmainer.budgetbook.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll()
            }
            .csrf { csrf -> csrf.disable() }
            .anonymous { anonymous ->
                anonymous.disable()
            }
        return http.build()
    }
}
