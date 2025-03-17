package kfd.cherkasov.lab4.mappers

import kfd.cherkasov.lab4.database.entities.User
import kfd.cherkasov.lab4.models.responses.UserDataResponse
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun entityToResponse(entity: User): UserDataResponse {
        return UserDataResponse(
            id = entity.id,
            login = entity.login,
            createdAt = entity.createdAt
        )
    }
}