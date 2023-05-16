package io.tokend.certificates.utils

import android.content.Context
import android.content.SharedPreferences
import io.tokend.certificates.R
import java.util.*

class AppLocaleManager(
    context: Context,
    private val preferences: SharedPreferences
) {
    private val defaultLocale = context.resources.getString(R.string.language_code)
        .let(::Locale)


    val availableLocales = listOf(
        // Do not forget to update resConfigs in build.gradle
        Locale("en"),
        Locale("ru"),
        Locale("uk")
    )

    fun getLocale(): Locale = loadLocale() ?: defaultLocale

    fun setLocale(locale: Locale) {
        if (locale == getLocale()) {
            return
        }

        saveLocale(locale)
        applyLocale(locale)
    }

    /**
     * Applies locale based on the stored or default one.
     * Call this method on app init
     */
    fun initLocale() {
        applyLocale(getLocale())
    }

    /**
     * @return [Context] set up to use current locale
     */
    fun getLocalizeContext(baseContext: Context): Context {
        return LocalizedContextWrapper.wrap(baseContext, getLocale())
    }

    private fun applyLocale(locale: Locale) {
        Locale.setDefault(locale)
    }

    private fun loadLocale(): Locale? {
        return preferences
            .getString(CURRENT_LOCALE_KEY, "")
            ?.takeIf(String::isNotEmpty)
            ?.let { Locale(it) }
    }

    private fun saveLocale(locale: Locale) {
        preferences
            .edit()
            .putString(CURRENT_LOCALE_KEY, locale.language)
            .apply()
    }

    private companion object {
        private const val CURRENT_LOCALE_KEY = "current_locale"
    }
}