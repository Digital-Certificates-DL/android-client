package io.tokend.certificates.data.datasource.api

import io.reactivex.Single
import io.tokend.certificates.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET

interface CertificateApi {
    @GET(BuildConfig.CERTIFICATE_API_URL)
    fun getCertificates(): Single<ResponseBody>
}