package kfd.cherkasov.lab4.models.responses

import java.time.LocalDateTime

data class TransactionDataResponse (
    val userId: Long,
    val pairId: Int,
    val currencyId: Int,
    val amount: Long,
    val isSuccessful: Boolean,
    val happenedAt: LocalDateTime
)