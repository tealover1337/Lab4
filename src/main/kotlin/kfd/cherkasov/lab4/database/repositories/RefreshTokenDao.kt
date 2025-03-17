package kfd.cherkasov.lab4.database.repositories

import kfd.cherkasov.lab4.database.entities.RefreshToken
import kfd.cherkasov.lab4.database.entities.User
import org.springframework.data.repository.CrudRepository

interface RefreshTokenDao : CrudRepository<RefreshToken, Long> {
    fun findByToken(token: String): RefreshToken?
    fun deleteByUserId(user: User)
}