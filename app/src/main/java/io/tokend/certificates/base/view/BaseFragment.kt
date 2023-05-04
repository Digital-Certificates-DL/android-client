package io.tokend.certificates.base.view

import android.content.Context
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.tokend.certificates.App
import io.tokend.certificates.di.providers.ApiProvider
import io.tokend.certificates.utils.ClickHelper
import io.tokend.certificates.view.ToastManager
import javax.inject.Inject

abstract class BaseFragment: Fragment() {
    @Inject
    lateinit var toastManager: ToastManager

    @Inject
    lateinit var clickHelper: ClickHelper

    @Inject
    lateinit var apiProvider: ApiProvider

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
