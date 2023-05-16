package io.tokend.certificates.di

import dagger.Module
import dagger.Provides
import io.tokend.certificates.di.providers.ApiProvider
import io.tokend.certificates.di.providers.ApiProviderImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiProviderModule {
    @Provides
    @Singleton
    fun getApiProvider(retrofit: Retrofit): ApiProvider {
        return ApiProviderImpl(retrofit)
    }
}