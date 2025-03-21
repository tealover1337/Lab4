package kfd.cherkasov.lab4.services

import kfd.cherkasov.lab4.database.entities.Wallet
import kfd.cherkasov.lab4.database.repositories.WalletDao
import kfd.cherkasov.lab4.mappers.WalletMapper
import kfd.cherkasov.lab4.models.requests.AddWalletRequest
import kfd.cherkasov.lab4.models.requests.UpdateWalletRequest
import kfd.cherkasov.lab4.models.responses.WalletDataResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletService(
    val walletDao: WalletDao,
    val walletMapper: WalletMapper,
) {
    fun getAllWallets(): List<WalletDataResponse> = walletDao.findAll().map { walletMapper.entityToResponse(it) }

    fun getWalletById(id: Long): WalletDataResponse = walletMapper.entityToResponse(walletDao.findWalletById(id)[0])

    @Transactional
    fun addWallet(request: AddWalletRequest): WalletDataResponse {
        val newWallet = Wallet(
            ownerId = request.ownerId,
            currencyId = request.currencyId
        )
        newWallet.balance = request.balance
        walletDao.save(newWallet)
        return walletMapper.entityToResponse(newWallet)
    }

    @Transactional
    fun updateBalanceById(id: Long, request: UpdateWalletRequest): WalletDataResponse {
        val wallet = walletDao.findWalletById(id)[0]
        wallet.balance = request.newBalance
        walletDao.save(wallet)
        return walletMapper.entityToResponse(wallet)
    }

    fun deleteWalletById(id: Long): WalletDataResponse {
        val deletedWallet = walletDao.findWalletById(id)[0]
        walletDao.delete(deletedWallet)
        return walletMapper.entityToResponse(deletedWallet)
    }

    fun getWalletByUserAndCurrency(userId: Long, currencyId: Int): Wallet {
        return walletDao.findWalletByOwnerIdAndCurrencyId(userId, currencyId)[0]
    }
}