package kfd.cherkasov.lab4.models.requests

data class AddWalletRequest (
    val ownerId: Long,
    val currencyId: Int,
    val balance: Long
)