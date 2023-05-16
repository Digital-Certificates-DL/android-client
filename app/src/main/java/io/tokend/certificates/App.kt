package io.tokend.certificates

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import io.tokend.certificates.di.*
import io.tokend.certificates.utils.AppLocaleManager


class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initLocale()
        initDi()
    }

    private fun initLocale() {
        mLocaleManager = AppLocaleManager(this, getAppPreferences())
        localeManager.initLocale()
    }

    private fun getAppPreferences(): SharedPreferences {
        return getSharedPreferences("App", Context.MODE_PRIVATE)
    }


    private fun initDi() {

        appComponent = DaggerAppComponent.builder()
            .utilsModule(
                UtilsModule(localeManager.getLocalizeContext(this))
            ).localeManagerModule(LocaleManagerModule(localeManager))
            .apiProviderModule(
                ApiProviderModule()
            ).build()
        appComponent.inject(this)
    }

    companion object {
        private lateinit var mLocaleManager: AppLocaleManager
        val localeManager: AppLocaleManager
            get() = mLocaleManager
    }
}