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
        val convertedAmount = amount.amount * rate
        return CurrencyAmount(convertedAmount)
    }

    private fun getExchangeRate(
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): Double {
        return when {
            fromCurrency.code == "USD" &&
                    toCurrency.code == "EUR" -> 0.93

            fromCurrency.code == "USD" &&
                    toCurrency.code == "GBP" -> 0.82

            else -> 1.0
        }
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

