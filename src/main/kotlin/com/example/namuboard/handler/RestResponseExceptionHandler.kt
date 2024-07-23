package com.example.namuboard.handler

import com.example.namuboard.dto.ErrorDto
import com.example.namuboard.exception.DuplicateMemberException
import com.example.namuboard.exception.NotFoundMemberException
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseExceptionHandler : ResponseEntityExceptionHandler() {
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = [DuplicateMemberException::class])
    @ResponseBody
    protected fun conflict(
        ex: RuntimeException,
        request: WebRequest?,
    ): ErrorDto = ErrorDto(CONFLICT.value(), ex.message)

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = [NotFoundMemberException::class, AccessDeniedException::class])
    @ResponseBody
    protected fun forbidden(
        ex: RuntimeException,
        request: WebRequest?,
    ): ErrorDto = ErrorDto(FORBIDDEN.value(), ex.message)
}
