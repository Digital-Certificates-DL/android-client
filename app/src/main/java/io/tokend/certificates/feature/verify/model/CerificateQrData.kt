package io.tokend.certificates.feature.verify.model


data class CertificateQrData(
    val message: String,
    val address: String,
    val signature: String,
    val certificatePage: String? = null
) : java.io.Serializable {
    override fun toString(): String {
        var result = "message:\n" +
                "${message}\n" +
                "\n" +
                "address:\n" +
                "${address}\n" +
                "\n" +
                "signature:\n" +
                "${signature}\n" +
                "\n"
        if (certificatePage != null) {
            result +=
                "certificate page:\n" +
                        "${certificatePage}\n"
        }
        return result
    }
}
