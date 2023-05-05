package io.tokend.certificates.feature.verify.logic

import io.tokend.certificates.feature.verify.model.CertificateQrData

object ParseCertificateUseCase {

    fun parse(raw: String): CertificateQrData {
        val input = raw.trimIndent()
        val lines = input.lines()

        val message = lines[1].trim()
        val address = lines[4].trim()
        val signature = lines[7].trim()

        return CertificateQrData(message, address, signature)
    }

}