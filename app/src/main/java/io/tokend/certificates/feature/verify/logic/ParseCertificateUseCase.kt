package io.tokend.certificates.feature.verify.logic

import io.tokend.certificates.feature.verify.model.CertificateQrData

object ParseCertificateUseCase {

    fun parse(raw: String): CertificateQrData {

        val input = raw.trimIndent()
        val lines = input.lines()

        val messageIndex = lines.indexOf("message:")
        val addressIndex = lines.indexOf("address:")
        val signatureIndex = lines.indexOf("signature:")
        val certificateIndex = lines.indexOf("certificate page:")

        if (messageIndex < 0 || addressIndex < 0 || signatureIndex < 0) {
            throw InvalidParsingException()
        }

        val message = lines[messageIndex + 1]
        val address = lines[addressIndex + 1]
        val signature = lines[signatureIndex + 1]
        var certificatePage = ""
        if (certificateIndex > 0)
            certificatePage = lines[certificateIndex + 1]

        return CertificateQrData(message, address, signature, input, certificatePage)
    }

    class InvalidParsingException : Exception()

}