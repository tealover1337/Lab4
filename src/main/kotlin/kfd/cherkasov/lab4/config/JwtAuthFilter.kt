package kfd.cherkasov.lab4.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kfd.cherkasov.lab4.database.repositories.UserDao
import kfd.cherkasov.lab4.exceptions.EntityNotFoundException
import kfd.cherkasov.lab4.exceptions.InvalidTokenException
import kfd.cherkasov.lab4.services.JwtTokenService
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtTokenService: JwtTokenService,
    private val userDao: UserDao
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val publicEndpoints = listOf(
            AntPathRequestMatcher("/auth/login", HttpMethod.POST.name()),
            AntPathRequestMatcher("/auth/refresh", HttpMethod.POST.name()),
            AntPathRequestMatcher("/auth/register", HttpMethod.POST.name())
        )

        fun isPublicEndpoint(request: HttpServletRequest): Boolean {
            return publicEndpoints.any { it.matches(request) }
        }

        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            throw InvalidTokenException("Invalid token header")
        }

        val jwt = authHeader.substring(7)
        val login = jwtTokenService.getLoginFromToken(jwt) ?: run {
            filterChain.doFilter(request, response)
            throw InvalidTokenException("Couldn't receive login from token")
        }
        val user = userDao.findUserByLogin(login)[0] ?: run {
            filterChain.doFilter(request, response)
            throw EntityNotFoundException("Didn't find a user with such login")
        }
        if (!jwtTokenService.validateToken(jwt, user)) {
            filterChain.doFilter(request, response)
            throw InvalidTokenException("Couldn't validate token")
        }
        val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }
        val auth = UsernamePasswordAuthenticationToken(
            user,
            null,
            authorities
        )

        SecurityContextHolder.getContext().authentication = auth

        filterChain.doFilter(request, response)
    }
}