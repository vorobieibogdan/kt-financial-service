package mate.academy

class FinancialService {

    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ): String {
        return "Transferred ${amount.amount} ${currencyCode.code} " +
                "from ${source.number} to ${destination.number}. " +
                "Transaction ID: ${transactionId.id}"
    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        val rate = getExchangeRate(fromCurrency, toCurrency)
        return CurrencyAmount(amount.amount * rate)
    }

    private fun getExchangeRate(
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): Double {
        return when {
            fromCurrency.code == USD &&
                    toCurrency.code == EUR -> USD_TO_EUR_RATE

            fromCurrency.code == USD &&
                    toCurrency.code == GBP -> USD_TO_GBP_RATE

            else -> DEFAULT_RATE
        }
    }

    private companion object {
        const val USD = "USD"
        const val EUR = "EUR"
        const val GBP = "GBP"

        const val USD_TO_EUR_RATE = 0.93
        const val USD_TO_GBP_RATE = 0.82
        const val DEFAULT_RATE = 1.0
    }
}

@JvmInline
value class CurrencyAmount(val amount: Double) {
    init {
        require(amount >= 0) {
            "Amount must be non-negative"
        }
    }
}

@JvmInline
value class CurrencyCode(val code: String) {
    init {
        require(code.matches(Regex("^[A-Z]{3}$"))) {
            "Currency code must be 3 uppercase letters"
        }
    }
}

@JvmInline
value class AccountNumber(val number: String) {
    init {
        require(number.matches(Regex("^\\d{10}$"))) {
            "Account number must be exactly 10 digits"
        }
    }
}

@JvmInline
value class TransactionId(val id: String) {
    init {
        require(id.isNotEmpty()) {
            "Transaction ID must not be empty"
        }
    }
}


