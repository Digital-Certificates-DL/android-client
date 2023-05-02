package io.tokend.certificates.data.datasource.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface BitcoinApi {
    @GET("https://blockchain.info/rawtx/{tx_hash}")
    fun getTransaction(@Path("tx_hash") transactionHash: String): Single<ResponseBody>
}