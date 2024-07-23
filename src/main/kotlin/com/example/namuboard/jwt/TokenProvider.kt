package com.example.namuboard.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

/**
 * 토큰의 생성, 토큰의 유효성 검증을 담당
 */
@Component
class TokenProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expirationMs}") private val tokenValidityInSeconds: Long,
) {
    /**
     * Authentication 객체를 받아서 토큰을 생성
     */
    fun createToken(authentication: Authentication): String {
        val now = Date().time
        val validity = Date(now + this.tokenValidityInSeconds)
        val key = getKey()

        return Jwts
            .builder()
            .subject(authentication.name)
            .claim(AUTHORITIES_KEY, authentication.authorities)
            .signWith(key)
            .expiration(validity)
            .compact()
    }

    /**
     * token을 받아서 Authentication 객체를 리턴
     */
    fun getAuthentication(token: String?): Authentication {
        val key = getKey()
        val claims: Claims =
            Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload

        val authorities: Collection<GrantedAuthority> =
            claims[AUTHORITIES_KEY]
                .toString()
                .split(",")
                .map { SimpleGrantedAuthority(it) }

        val principal: User = User(claims.subject, "", authorities)

        println(token)
        println(authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    /**
     * token의 유효성을 검증
     */
    fun validateToken(token: String?): Boolean {
        println("validateToken")
        try {
            val key = getKey()
            // jwt 토큰 파싱과 동시에 validation 체크
            Jwts
                .parser()
                .verifyWith(key)
            println("validateToken return true")
            return true
        } catch (e: SecurityException) {
            println("JWT 토큰이 잘못되었습니다.")
        }
        println("validateToken return false")
        return false
    }

    private fun getKey(): SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    companion object {
        const val AUTHORITIES_KEY = "auth"
    }
}
