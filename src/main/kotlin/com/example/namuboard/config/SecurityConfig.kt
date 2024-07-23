@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.namuboard.config

import com.example.namuboard.jwt.JwtAccessDeniedHandler
import com.example.namuboard.jwt.JwtAuthenticationEntryPoint
import com.example.namuboard.jwt.JwtSecurityConfig
import com.example.namuboard.jwt.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val corsFilter: CorsFilter,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            // cors 필터와 예외처리 추가
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            // 특정한 API 에 대하여 권한 설정
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/api/hello",
                        "/api/authenticate",
                        "/api/signup",
                        "v3/api-docs/**",
                        "swagger-ui/**",
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
            }
            // 세션을 사용하지 않기 때문에 STATELESS 로 설정
            .sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS,
                )
            }
            // jwt 설정
            .with(
                JwtSecurityConfig(tokenProvider),
            ) { }
        return http.build()
    }
}
