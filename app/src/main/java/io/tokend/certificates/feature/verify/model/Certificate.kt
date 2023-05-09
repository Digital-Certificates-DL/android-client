package io.tokend.certificates.feature.verify.model

import org.json.JSONObject

data class CourseCertificate(
    val date: String,
    val participant: String,
    val courseTitle: String,
    val points: String,
    val note: String,
    val serialNumber: String,
    val certificate: String,
    val dataHash: String,
    val transactionHash: String,
    val signature: String,
    val digitalCertificate: String
) {
    companion object {
        fun fromJson(jsonString: String): List<CourseCertificate> {

            val jsonArray = JSONObject(jsonString).getJSONArray("result1")
            val courseCertificates = mutableListOf<CourseCertificate>()

            for (i in 1 until jsonArray.length()) {
                val innerJsonArray = jsonArray.getJSONArray(i)
                val courseCertificate = CourseCertificate(
                    date = innerJsonArray.getString(0),
                    participant = innerJsonArray.getString(1),
                    courseTitle = innerJsonArray.getString(2),
                    points = innerJsonArray.getString(3),
                    note = innerJsonArray.getString(4),
                    serialNumber = innerJsonArray.getString(5),
                    certificate = innerJsonArray.getString(6),
                    dataHash = innerJsonArray.getString(7),
                    transactionHash = innerJsonArray.getString(8),
                    signature = innerJsonArray.getString(9),
                    digitalCertificate = innerJsonArray.getString(10)
                )
                courseCertificates.add(courseCertificate)
            }
            return courseCertificates
        }
    }
}
