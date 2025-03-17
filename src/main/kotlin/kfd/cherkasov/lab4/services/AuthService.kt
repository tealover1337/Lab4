package kfd.cherkasov.lab4.services

import kfd.cherkasov.lab4.config.JwtProperties
import kfd.cherkasov.lab4.database.entities.RefreshToken
import kfd.cherkasov.lab4.database.repositories.RefreshTokenDao
import kfd.cherkasov.lab4.database.repositories.UserDao
import kfd.cherkasov.lab4.exceptions.CredentialMismatchException
import kfd.cherkasov.lab4.exceptions.EntityNotFoundException
import kfd.cherkasov.lab4.exceptions.InvalidTokenException
import kfd.cherkasov.lab4.exceptions.TokenExpiredException
import kfd.cherkasov.lab4.models.requests.RefreshTokenRequest
import kfd.cherkasov.lab4.models.requests.RegisterUserRequest
import kfd.cherkasov.lab4.models.responses.AuthTokensResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthService(
    private val jwtProperties: JwtProperties,
    private val userDao: UserDao,
    passwordEncoderClass: UserService.PasswordEncoder,
    private val refreshTokenDao: RefreshTokenDao,
    private val jwtTokenService: JwtTokenService,
    private val userService: UserService
) {

    val passwordEncoder = passwordEncoderClass.getPasswordEncoder()

    @Transactional
    fun authUser(authRequest: RegisterUserRequest): AuthTokensResponse { //Yes, register, but they are the same
        val user = userDao.findUserByLogin(authRequest.login)[0] ?: throw EntityNotFoundException("User not found")

        if (!passwordEncoder.matches(authRequest.password, user.passwordHash))
            throw CredentialMismatchException("Password mismatch")


        refreshTokenDao.deleteByUserId(user)

        val accessToken = jwtTokenService.createAccessToken(user)
        val refreshToken = jwtTokenService.createRefreshToken(user)

        refreshTokenDao.save(
            RefreshToken(
                token = refreshToken,
                expiresAt = jwtTokenService.getRefreshTokenExpirationDate(),
                userId = user
            )
        )

        return AuthTokensResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun register(request: RegisterUserRequest) {
        userService.createUser(request)
    }

    fun refreshAccessToken(refreshTokenRequest: RefreshTokenRequest): AuthTokensResponse {
        val refreshToken = refreshTokenDao.findByToken(refreshTokenRequest.refreshToken)
            ?: throw InvalidTokenException("Invalid refresh token")

        if (refreshToken.expiresAt.before(Date())) {
            refreshTokenDao.delete(refreshToken)
            throw TokenExpiredException("Refresh token expired")
        }

        val user = refreshToken.userId
        refreshTokenDao.delete(refreshToken)

        val newAccessToken = jwtTokenService.createAccessToken(user)
        val newRefreshToken = jwtTokenService.createRefreshToken(user)

        refreshTokenDao.save(
            RefreshToken(
                token = newRefreshToken,
                expiresAt = jwtTokenService.getRefreshTokenExpirationDate(),
                userId = user
            )
        )

        return AuthTokensResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }
}