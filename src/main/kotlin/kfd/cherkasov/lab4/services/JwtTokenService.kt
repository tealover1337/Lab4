package kfd.cherkasov.lab4.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import kfd.cherkasov.lab4.config.JwtProperties
import kfd.cherkasov.lab4.database.entities.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class JwtTokenService (
    val jwtProperties: JwtProperties
) {
    val secretKey = Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())

    fun createToken(user: User, expirationDate: Date, claims: Map<String, Any> = mapOf()): String =
        Jwts.builder()
            .claims(claims)
            .subject(user.login)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .signWith(secretKey)
            .compact()

    fun getAccessTokenExpirationDate(): Date = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpirationDate)
    fun getRefreshTokenExpirationDate(): Date = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpirationDate)

    fun createAccessToken(user: User) = createToken(
        user,
        getAccessTokenExpirationDate(),
        mapOf("roles" to user.roles.map { it.name })
    )

    fun createRefreshToken(user: User) = createToken(
        user,
        getRefreshTokenExpirationDate(),
        mapOf("roles" to user.roles.map { it.name })
    )

    fun getAllClaimsFromToken(token: String): Claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload

    fun getLoginFromToken(token: String): String? = getAllClaimsFromToken(token).subject

    fun isTokenExpired(token: String): Boolean = try {
        getAllClaimsFromToken(token).expiration.before(Date(System.currentTimeMillis()))
        false
    } catch (e: ExpiredJwtException) {
        true
    }

    fun validateToken(token: String, user: User): Boolean {
        val login = getLoginFromToken(token)

        return login == user.login && !isTokenExpired(token)
    }

}
