package kfd.cherkasov.lab4.services

import kfd.cherkasov.lab4.database.entities.User
import kfd.cherkasov.lab4.exceptions.UnauthorizedException
import org.springframework.security.access.AuthorizationServiceException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityService {
    fun getAuthenticatedUserFromContext(): User {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("Authentication required")

        return when (val principal = authentication.principal) {
            is User -> principal
            else -> throw AuthorizationServiceException("Invalid principal type: ${principal?.javaClass?.simpleName}")
        }
    }
}