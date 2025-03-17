package kfd.cherkasov.lab4.controllers

import kfd.cherkasov.lab4.models.requests.RefreshTokenRequest
import kfd.cherkasov.lab4.models.requests.RegisterUserRequest
import kfd.cherkasov.lab4.services.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterUserRequest) = ResponseEntity
        .status(HttpStatus.OK)
        .body(authService.register(request))

    @PostMapping("/login")
    fun authUser(@RequestBody request: RegisterUserRequest) = ResponseEntity
        .status(HttpStatus.OK)
        .body(authService.authUser(request))

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequest) = ResponseEntity
        .status(HttpStatus.OK)
        .body(authService.refreshAccessToken(request))
}