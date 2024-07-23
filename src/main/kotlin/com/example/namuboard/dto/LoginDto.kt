package com.example.namuboard.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class LoginDto(
    @Schema(description = "아이디", example = "test")
    @NotNull
    @Size(min = 3, max = 50)
    var username: String,
    @Schema(description = "비밀번호", example = "test")
    @NotNull
    @Size(min = 3, max = 100)
    var password: String,
)
