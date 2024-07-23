package com.example.namuboard.dto

import com.example.namuboard.domain.user.entity.User
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.stream.Collectors

data class UserDto(
    @Schema(description = "사용자 이름", example = "test")
    @Parameter(description = "사용자 이름")
    @NotNull
    @Size(min = 3, max = 50)
    val username: String,
    @Schema(description = "비밀번호", example = "test")
    @Parameter(description = "비밀번호")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    val password: String,
    @Schema(description = "닉네임", example = "test")
    @Parameter(description = "닉네임")
    @NotNull
    @Size(min = 3, max = 50)
    val nickname: String,
    @Schema(hidden = true)
    private val authorities: Set<String> = mutableSetOf(),
) {
    companion object {
        fun from(user: User?): UserDto? {
            if (user == null) return null

            return UserDto(
                username = user.username,
                password = user.password,
                nickname = user.nickname,
                authorities =
                    user.authorities
                        .stream()
                        .map { authority -> authority.authorityName }
                        .collect(Collectors.toSet()),
            )
        }
    }
}
