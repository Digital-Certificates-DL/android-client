package io.tokend.certificates.di.providers

import io.tokend.certificates.data.datasource.api.BitcoinApi
import io.tokend.certificates.data.datasource.api.CertificateApi
import retrofit2.Retrofit

class ApiProviderImpl(
    private val retrofit: Retrofit
) : ApiProvider {

    override val certificateApi: CertificateApi by lazy {
        retrofit.create(CertificateApi::class.java)
    }

    override val bitcoinApi: BitcoinApi by lazy {
        retrofit.create(BitcoinApi::class.java)
    }
}