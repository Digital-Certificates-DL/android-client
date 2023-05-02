package io.tokend.certificates.feature.home.logic

import com.google.gson.Gson
import io.reactivex.Single
import io.tokend.certificates.di.providers.ApiProvider
import io.tokend.certificates.extensions.decodeHex
import io.tokend.certificates.feature.home.model.CourseCertificate
import io.tokend.certificates.feature.verify.logic.BitcoinVerifier
import io.tokend.certificates.feature.verify.model.CertificateData
import io.tokend.certificates.feature.verify.model.Transaction

class VerifyCertificateUseCase(private val apiProvider: ApiProvider) {

    fun verify(certificate: CertificateData): Single<Boolean> {

        return Single.fromCallable {
            BitcoinVerifier.verifyBitcoinMessage(
                certificate.message,
                certificate.address,
                certificate.signature
            )

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

            if (networkCertificate == null) {
                return@fromCallable false
            }

            if (networkCertificate.transactionHash.length != 64) {
                return@fromCallable false
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

            false
        }
    }
}