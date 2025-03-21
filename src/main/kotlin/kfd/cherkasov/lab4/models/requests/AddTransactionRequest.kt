package kfd.cherkasov.lab4.models.requests

data class AddTransactionRequest(
    val userId: Long,
    val currencyToSellId: Int,
    val currencyToBuyId: Int,
    val amount: Long,
    val isSuccessful: Boolean
)