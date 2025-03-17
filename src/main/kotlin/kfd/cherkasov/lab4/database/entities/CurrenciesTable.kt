package kfd.cherkasov.lab4.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "currencies")
class Currency(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "code_name", nullable = false)
    val codeName: String,

) {
    @Column(name = "market_value", nullable = false)
    private var marketValue: Long = 0L

    fun getMarketValue(): Long { return marketValue }
    fun setMarketValue(newMarketValue: Long): Unit {this.marketValue = newMarketValue}

    @Column(name = "bank_amount", nullable = false)
    private var bankAmount: Long = 0L

    fun getBankAmount(): Long { return bankAmount }
    fun setBankAmount(newBankAmount: Long): Unit {this.bankAmount = newBankAmount}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
}