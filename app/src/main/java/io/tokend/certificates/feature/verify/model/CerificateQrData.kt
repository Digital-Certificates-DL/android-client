package io.tokend.certificates.feature.verify.model


data class CertificateQrData(
    val message: String,
    val address: String,
    val signature: String,
    val originalString: String,
    val certificatePage: String? = null
) : java.io.Serializable {
    override fun toString(): String {
        return originalString
    }
}
