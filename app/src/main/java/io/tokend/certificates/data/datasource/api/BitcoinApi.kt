package io.tokend.certificates.data.datasource.api

import io.reactivex.Single
import io.tokend.certificates.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface BitcoinApi {
    @GET("${BuildConfig.BITCOIN_API_URL}/rawtx/{tx_hash}")
    fun getTransaction(@Path("tx_hash") transactionHash: String): Single<ResponseBody>
}