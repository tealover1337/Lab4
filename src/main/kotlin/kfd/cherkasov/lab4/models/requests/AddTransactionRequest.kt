package kfd.cherkasov.lab4.models.requests

data class AddTransactionRequest (
    val userId: Long,
    val pairId: Int,
    val currencyId: Int,
    val amount: Long,
    val isSuccessful: Boolean
)