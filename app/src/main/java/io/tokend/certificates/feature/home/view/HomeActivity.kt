package io.tokend.certificates.feature.home.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import io.tokend.certificates.R
import io.tokend.certificates.base.view.BaseActivity
import io.tokend.certificates.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreateAllowed(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        initStartFragment()
        initNavigationBar()
    }


    private fun initNavigationBar() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val fragment = HomePageFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, fragment)
                .commit()
            true
        }
    }

    private fun initStartFragment() {
        val fragment = HomePageFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, fragment)
            .commit()
    }
}