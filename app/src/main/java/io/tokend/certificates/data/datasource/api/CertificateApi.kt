package io.tokend.certificates.data.datasource.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface CertificateApi {
    // TODO: implement endPoint
    @GET("")
    fun getCertificates() : Single<ResponseBody>
}