package kfd.cherkasov.lab4.mappers

import kfd.cherkasov.lab4.database.entities.Transaction
import kfd.cherkasov.lab4.models.responses.TransactionDataResponse
import org.springframework.stereotype.Component

@Component
class TransactionMapper {
    fun entityToResponse(entity: Transaction): TransactionDataResponse {
        return TransactionDataResponse(
            userId = entity.userId,
            pairId = entity.pairId,
            currencyId = entity.currencyId,
            amount = entity.amount,
            isSuccessful = entity.isSuccessfull,
            happenedAt = entity.createdAt
        )
    }
}