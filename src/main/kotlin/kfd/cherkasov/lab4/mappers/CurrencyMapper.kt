package kfd.cherkasov.lab4.mappers

import kfd.cherkasov.lab4.database.entities.Currency
import kfd.cherkasov.lab4.models.responses.CurrencyDataResponse
import org.springframework.stereotype.Component

@Component
class CurrencyMapper {
    fun entityToResponse(currency: Currency): CurrencyDataResponse {
        return CurrencyDataResponse(
            name = currency.name,
            codeName = currency.codeName,
            bankAmount = currency.getBankAmount(),
            marketValue = currency.getMarketValue(),
            createdAt = currency.createdAt
        )
    }
}