package kfd.cherkasov.lab4.database.repositories

import kfd.cherkasov.lab4.database.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDao : CrudRepository<User, Long> {
    fun getUserById(id: Long): MutableList<User?>
    fun findUserByLogin(login: String): MutableList<User?>
}