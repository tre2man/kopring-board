package com.example.namuboard.dto

import io.swagger.v3.oas.annotations.media.Schema

data class TokenDto(
    @Schema(description = "토큰", example = "eyJhbGciOiJIUzI1NiJ9")
    var token: String,
)
