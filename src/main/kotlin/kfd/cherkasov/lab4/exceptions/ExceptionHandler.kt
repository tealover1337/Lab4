package kfd.cherkasov.lab4.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(NotUniqueRegisterDataException::class)
    fun handleNURDE(e: NotUniqueRegisterDataException) = ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(e.message)

    @ExceptionHandler(CredentialMismatchException::class)
    fun foo(e: CredentialMismatchException) = ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(e.message)

    @ExceptionHandler(EntityNotFoundException::class)
    fun bar(e: EntityNotFoundException) = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(e.message)

    @ExceptionHandler(UnauthorizedException::class)
    fun foobar(e: UnauthorizedException) = ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(e.message)

    @ExceptionHandler(InvalidTokenException::class)
    fun fofobar(e: InvalidTokenException) = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(e.message)

    @ExceptionHandler(TokenExpiredException::class)
    fun fofofobar(e: TokenExpiredException) = ResponseEntity
        .status(HttpStatus.REQUEST_TIMEOUT)
        .body(e.message)

    @ExceptionHandler(NullPointerException::class)
    fun sos(e: NullPointerException) = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(e.cause)

    @ExceptionHandler(IndexOutOfBoundsException::class)
    fun lol(e: IndexOutOfBoundsException) = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(e.cause)
}