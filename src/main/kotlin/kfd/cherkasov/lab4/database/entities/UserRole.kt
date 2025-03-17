package kfd.cherkasov.lab4.database.entities

import jakarta.persistence.*
import kfd.cherkasov.lab4.database.repositories.RoleDao
import org.hibernate.annotations.CreationTimestamp
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Entity
@Table(name = "roles")
class UserRole(
    @Column(name = "name", nullable = false, unique = true)
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()

    enum class RoleType {
        ROLE_USER,
    }
}

@Component
class RoleInit(
    private val roleDao: RoleDao,
) {
    @EventListener(ContextRefreshedEvent::class)
    @Transactional
    fun initRoles() {
        roleDao.save(UserRole(name = "ROLE_USER"))
    }
}