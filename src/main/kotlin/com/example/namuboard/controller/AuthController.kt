package com.example.namuboard.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Controller("api/auth")
class AuthController {
    @PostMapping("/signup")
    fun signup() {
    }

    @PostMapping("/login")
    fun login() {
    }

    @PostMapping("/logout")
    fun logout() {
    }

    @PostMapping("/refresh")
    fun refresh() {
    }
}