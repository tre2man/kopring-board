package com.example.namuboard.service

import com.example.namuboard.domain.authority.entity.Authority
import com.example.namuboard.domain.user.entity.User
import com.example.namuboard.domain.user.repository.UserRepository
import com.example.namuboard.dto.UserDto
import com.example.namuboard.exception.DuplicateMemberException
import com.example.namuboard.exception.NotFoundMemberException
import com.example.namuboard.util.SecurityUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun signup(userDto: UserDto): UserDto {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.username).orElse(null) != null) {
            throw DuplicateMemberException("이미 가입되어 있는 유저입니다.")
        }

        val authority = Authority.of("ROLE_USER")

        val user: User =
            User.of(
                userDto.username,
                passwordEncoder.encode(userDto.password),
                userDto.nickname,
                authority,
            )

        val result = UserDto.from(userRepository.save(user)) ?: throw RuntimeException("회원가입에 실패하였습니다.")

        return result
    }

    @Transactional(readOnly = true)
    fun getUserWithAuthorities(username: String) = UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null))

    @Transactional(readOnly = true)
    fun getMyUserWithAuthorities(): UserDto? {
        println("Hello, World!")
        return UserDto.from(
            SecurityUtil
                .getCurrentUserName
                .flatMap { username: String -> userRepository.findOneWithAuthoritiesByUsername(username) }
                .orElseThrow { NotFoundMemberException("Member not found") },
        )
    }
}
