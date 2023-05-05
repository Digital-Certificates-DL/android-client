package io.tokend.certificates.feature.verify.logic

import com.google.gson.Gson
import io.reactivex.Single
import io.tokend.certificates.di.providers.ApiProvider
import io.tokend.certificates.extensions.decodeHex
import io.tokend.certificates.feature.home.model.CourseCertificate
import io.tokend.certificates.feature.verify.model.CertificateQrData
import io.tokend.certificates.feature.verify.model.Transaction
import org.bitcoinj.core.Address
import org.bitcoinj.core.Base58
import org.bitcoinj.core.ECKey.signedMessageToKey
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.script.Script

class VerifyCertificateUseCase(private val apiProvider: ApiProvider) {

    private val mainAddress = "1BooKnbm48Eabw3FdPgTSudt9u4YTWKBvf"

    private fun verifyBitcoinMessage(message: String, address: String, signature: String): Boolean {
        if (mainAddress != address) {
            return false
        }
        val ecKey = signedMessageToKey(message, signature)
        val params = NetworkParameters.fromID(NetworkParameters.ID_MAINNET)
        val addressV =
            Base58.encodeChecked(0, Address.fromKey(params, ecKey!!, Script.ScriptType.P2PKH).hash)
        if (address == addressV) return true
        return false
    }

    fun verify(certificate: CertificateQrData): Single<Boolean> {

        return Single.fromCallable {
            val isVerified = verifyBitcoinMessage(
                certificate.message,
                certificate.address,
                certificate.signature
            )
            if (!isVerified) {
                //NOT_VERIFIED
                return@fromCallable false
            }

            val certificateList = CourseCertificate.fromJson(
                apiProvider.certificateApi
                    .getCertificates()
                    .blockingGet()
                    .string()
            )

            var networkCertificate: CourseCertificate? = null

            for (i in certificateList) {
                if (i.signature == certificate.signature) {
                    networkCertificate = i
                    break
                }
            }

            //NOT_FOUND
            if (networkCertificate == null) {
                return@fromCallable false
            }
            //NO_TIMESTEMPING OK
            if (networkCertificate.transactionHash.length != 64) {
                return@fromCallable true
            }

            val json = apiProvider.bitcoinApi.getTransaction(networkCertificate.transactionHash)
                .blockingGet().string()
            val gson = Gson()

            val transaction = gson.fromJson(json, Transaction::class.java)

            for (item in transaction.out) {
                if (item.addr == null) {
                    val encodedScript = item.script.decodeHex().drop(3)
                    if (certificate.message.contains(encodedScript))
                        return@fromCallable true
                }
            }

            return@fromCallable false
        }
    }
}