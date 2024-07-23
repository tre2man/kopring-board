package com.example.namuboard.handler

import com.example.namuboard.dto.ErrorDto
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class MethodArgumentNotValidExceptionHandler {
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ErrorDto {
        val result = ex.bindingResult
        val fieldErrors = result.fieldErrors
        return processFieldErrors(fieldErrors)
    }

    private fun processFieldErrors(fieldErrors: List<FieldError>): ErrorDto {
        val errorDTO: ErrorDto = ErrorDto(BAD_REQUEST.value(), "@Valid Error")
        for (fieldError in fieldErrors) {
            errorDTO.addFieldError(fieldError.objectName, fieldError.field, fieldError.defaultMessage)
        }
        return errorDTO
    }
}
