package io.tokend.certificates.di.providers

import io.tokend.certificates.data.datasource.api.BitcoinApi
import io.tokend.certificates.data.datasource.api.CertificateApi

interface ApiProvider {
    val certificateApi: CertificateApi
    val bitcoinApi: BitcoinApi
}