@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.namuboard.controller

import com.example.namuboard.dto.UserDto
import com.example.namuboard.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.io.IOException

@RestController
@RequestMapping("/api")
@Tag(name = "유저", description = "유저와 관련된 API")
class UserController(
    private val userService: UserService,
) {
    @Operation(summary = "hello", description = "hello")
    @ApiResponse(responseCode = "200", description = "hello", content = [])
    @GetMapping("/hello")
    fun hello(): ResponseEntity<String> = ResponseEntity.ok("hello")

    @Operation(summary = "test-redirect", description = "test-redirect")
    @ApiResponse(responseCode = "302", description = "test-redirect", content = [])
    @PostMapping("/test-redirect")
    @Throws(IOException::class)
    fun testRedirect(response: HttpServletResponse) {
        response.sendRedirect("/api/user")
    }

    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입 성공", content = [])
    @PostMapping("/signup")
    fun signup(
        @RequestBody userDto: @Valid UserDto,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(userService.signup(userDto))

    @Operation(summary = "내 정보", description = "내 정보")
    @ApiResponse(responseCode = "200", description = "내 정보", content = [])
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    fun getMyUserInfo(request: HttpServletRequest?): ResponseEntity<UserDto> = ResponseEntity.ok(userService.getMyUserWithAuthorities())

    @Operation(summary = "유저 정보", description = "유저 정보")
    @ApiResponse(responseCode = "200", description = "유저 정보", content = [])
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun getUserInfo(
        @PathVariable username: String,
    ): ResponseEntity<UserDto> = ResponseEntity.ok(userService.getUserWithAuthorities(username))
}
