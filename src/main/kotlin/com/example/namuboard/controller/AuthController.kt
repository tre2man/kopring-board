package com.example.namuboard.controller

import com.example.namuboard.dto.LoginDto
import com.example.namuboard.dto.TokenDto
import com.example.namuboard.jwt.JwtFilter
import com.example.namuboard.jwt.TokenProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "AuthController")
@RestController
@RequestMapping("/api")
class AuthController(
    private val tokenProvider: TokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
) {
    @Operation(summary = "로그인", description = "로그인")
    @ApiResponse(responseCode = "200", description = "authenticate", content = [])
    @PostMapping("/authenticate")
    fun authorize(
        @RequestBody loginDto: @Valid LoginDto?,
    ): ResponseEntity<TokenDto?> {
        val authenticationToken =
            UsernamePasswordAuthenticationToken(loginDto!!.username, loginDto.password)

        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.createToken(authentication)

        val httpHeaders: HttpHeaders = HttpHeaders()
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer $jwt")

        return ResponseEntity(TokenDto(jwt), httpHeaders, HttpStatus.OK)
    }
}
