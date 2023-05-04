package io.tokend.certificates.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.tokend.certificates.utils.AppLocaleManager
import io.tokend.certificates.utils.ClickHelper
import io.tokend.certificates.view.ToastManager


@Module
class UtilsModule(private val context: Context) {

    @Provides
    fun context(localeManager: AppLocaleManager): Context {
        return localeManager.getLocalizeContext(context)
    }

    @Provides
    fun toastManager(context: Context): ToastManager {
        return ToastManager(context)
    }

    @Provides
    fun getClickHelper(): ClickHelper {
        return ClickHelper(350)
    }
}