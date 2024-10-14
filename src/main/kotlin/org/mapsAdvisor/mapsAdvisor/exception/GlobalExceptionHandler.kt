package org.mapsAdvisor.mapsAdvisor.exception

import jakarta.validation.ConstraintViolationException
import org.mapsAdvisor.mapsAdvisor.model.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(
        ConstraintViolationException::class, // for @PathVariable, Request-mapping methods arguments
        MethodArgumentNotValidException::class, // for @RequestBody with @Valid
        BindException::class,
        ServletRequestBindingException::class,
        MethodArgumentTypeMismatchException::class,
        MissingServletRequestParameterException::class,
        MissingServletRequestPartException::class,
        HttpMessageConversionException::class,
        HttpMediaTypeNotSupportedException::class, // unsupported content-type
        //IllegalArgumentException::class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestExceptions(
        ex: Exception,
    ): ResponseEntity<ErrorMessage> {
        val message = when (ex) {
            is MethodArgumentNotValidException -> {
                ex.bindingResult.fieldErrors.joinToString(", ") {
                    "'${it.field}' value as '${it.rejectedValue}' rejected. ${it.defaultMessage}"
                }
            }

            is BindException -> {
                ex.fieldErrors.joinToString(",") {
                    "'${it.field}' value as '${it.rejectedValue}' rejected. ${it.defaultMessage}"
                }
            }

            is ConstraintViolationException -> {
                ex.constraintViolations.joinToString(", ") { it.message }
            }

            is MethodArgumentTypeMismatchException -> {
                val typeName = ex.requiredType?.let {
                    if (it.isPrimitive) it.name else it.simpleName
                } ?: "<unknown>"
                "'${ex.name}' value of type must be '${typeName.lowercase()}'. ${ex.cause?.message}"
            }

            else -> ex.message
        }
        return ResponseEntity(
            ErrorMessage(HttpStatus.BAD_REQUEST.value(), message ?: "Bad Request", ex.message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFoundException(ex: NotFoundException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)

    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerError(ex: Exception): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred. Please try again later."
        )

        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
