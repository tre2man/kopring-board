package com.example.namuboard.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Controller("api/user")
class UserController {
    @GetMapping("/me")
    fun getMe() {
    }
}