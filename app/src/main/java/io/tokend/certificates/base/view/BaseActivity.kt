package io.tokend.certificates.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.tokend.certificates.App
import io.tokend.certificates.di.providers.ApiProvider
import io.tokend.certificates.utils.ClickHelper
import io.tokend.certificates.view.ToastManager
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var toastManager: ToastManager

    @Inject
    lateinit var clickHelper: ClickHelper

    @Inject
    lateinit var apiProvider: ApiProvider

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        onCreateAllowed(savedInstanceState)

    }


    abstract fun onCreateAllowed(savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}