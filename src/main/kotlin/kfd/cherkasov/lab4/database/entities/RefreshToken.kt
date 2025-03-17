package kfd.cherkasov.lab4.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
class RefreshToken (
    @Column(unique = true, nullable = false)
    val token: String,

    @Column(nullable = false)
    val expiresAt: Date,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id" ,nullable = false)
    val userId: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
}