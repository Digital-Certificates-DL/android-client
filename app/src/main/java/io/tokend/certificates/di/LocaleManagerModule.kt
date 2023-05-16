package io.tokend.certificates.di

import dagger.Module
import dagger.Provides
import io.tokend.certificates.utils.AppLocaleManager
import javax.inject.Singleton

@Module
class LocaleManagerModule(
    private val localeManager: AppLocaleManager
) {
    @Provides
    @Singleton
    fun localeManager() = localeManager
}