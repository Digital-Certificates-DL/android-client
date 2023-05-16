package io.tokend.certificates.di

import dagger.Component
import io.tokend.certificates.App
import io.tokend.certificates.base.view.BaseActivity
import io.tokend.certificates.base.view.BaseFragment
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        UtilsModule::class,
        LocaleManagerModule::class,
        ApiProviderModule::class,
        RetrofitModule::class
    ]
)

interface AppComponent {
    fun inject(app: App)
    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
}