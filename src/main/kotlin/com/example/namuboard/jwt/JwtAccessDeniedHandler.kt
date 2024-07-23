package com.example.namuboard.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

// 필요 권한 없을때 403 forbidden 에러 반환
@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        println("JwtAccessDeniedHandler.handle")
        response.sendError(HttpServletResponse.SC_FORBIDDEN)
    }
}
