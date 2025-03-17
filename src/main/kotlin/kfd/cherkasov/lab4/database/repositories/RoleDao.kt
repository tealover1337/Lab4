package kfd.cherkasov.lab4.database.repositories

import kfd.cherkasov.lab4.database.entities.UserRole
import org.springframework.data.repository.CrudRepository

interface RoleDao : CrudRepository<UserRole, Long> {
    fun findByName(name: String): MutableList<UserRole>
}