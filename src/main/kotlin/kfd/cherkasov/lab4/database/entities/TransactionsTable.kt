package kfd.cherkasov.lab4.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
class Transaction(
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "currency_id", nullable = false)
    val currencyId: Int,

    @Column(name = "amount", nullable = false)
    val amount: Long,

    @Column(name = "pair_id", nullable = false)
    val pairId: Int,

    @Column(name = "is_succesful", nullable = false)
    val isSuccessfull: Boolean,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
}