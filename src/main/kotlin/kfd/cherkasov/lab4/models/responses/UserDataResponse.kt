package kfd.cherkasov.lab4.models.responses

import java.time.LocalDateTime

data class UserDataResponse(
    val id: Long,
    val login: String,
    val createdAt: LocalDateTime
)