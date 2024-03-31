package com.qaizen.car_rental_qaizen
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.callback.DarajaResult
import com.androidstudy.daraja.util.Environment
import com.androidstudy.daraja.util.TransactionType
import java.util.Calendar
import java.util.UUID

object MpesaUtil {

    fun generateUUID(): String =
        UUID.randomUUID().toString()

    val passKey: String
        get() = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"

    fun saveAccessToken(context: Context, accessToken: String) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.HOUR, 1)
        val oneHourAfter = cal.timeInMillis

        val mSettings = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        val editor = mSettings.edit()

        editor.putString("accessToken", accessToken)
        editor.putLong("expiryDate", oneHourAfter)
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        return if (expired(context)) {
            null
        } else {
            val mSettings = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
            mSettings.getString("accessToken", "")
        }
    }

    private fun expired(context: Context): Boolean {
        val mSettings = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        val expiryTime = mSettings.getLong("expiryDate", 0)
        val currentTime = Calendar.getInstance().timeInMillis
        return currentTime > expiryTime
    }
}



private fun getDaraja(): Daraja {
    return Daraja.builder(
        "tZQiEqJaGg6QBLP1bp1Yep7kopopJNDg9HKAG1oYH4AnV3O9",
        "0gJLnizluV3poRMLFfadr5ErjaU7boAsIP56N1h4nP7oDmyIRWWxyEAl1NcqADrB"
    )
        .setBusinessShortCode("5323577") //TODO: replace with your business short code
        .setPassKey(MpesaUtil.passKey)
        .setTransactionType(TransactionType.CustomerBuyGoodsOnline)
        .setCallbackUrl("http://mycallbackurl.com/checkout.php")
        .setEnvironment(Environment.PRODUCTION)
        .build()
}

fun initiatePayment(
    context: Context,
    phoneNumber: String,
    amount: Int,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
) {
    val daraja = getDaraja()
    daraja.getAccessToken { darajaResult ->
        when (darajaResult) {
            is DarajaResult.Success -> {
                // initiate payment
                daraja.initiatePayment(
                    darajaResult.value.access_token,
                    phoneNumber,
                    amount.toString(),
                    MpesaUtil.generateUUID(),
                    "Payment"
                ) { darajaResult ->
                    when (darajaResult) {
                        is DarajaResult.Success -> {
                            val result = darajaResult.value
                        }

                        is DarajaResult.Failure -> {
                            val exception = darajaResult.darajaException
                            if (darajaResult.isNetworkError) {
                                onError(exception?.message ?: "Network error!")
                            } else {
                                onError(exception?.message ?: "Payment failed!")
                            }
                        }
                    }
                }
            }

            is DarajaResult.Failure -> {
                onError(darajaResult.darajaException?.message ?: "Payment failed!")
            }
        }
    }
}