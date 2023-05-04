package io.tokend.certificates.feature.verify.model


data class CertificateQrData(
    val message: String,
    val address: String,
    val signature: String,
    val certificatePage: String? = null
) : java.io.Serializable
