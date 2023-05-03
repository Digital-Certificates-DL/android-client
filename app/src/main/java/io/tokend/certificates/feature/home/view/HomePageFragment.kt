package io.tokend.certificates.feature.home.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.rxkotlin.addTo
import io.tokend.certificates.R
import io.tokend.certificates.base.view.BaseFragment
import io.tokend.certificates.databinding.FragmentHomePageBinding
import io.tokend.certificates.feature.home.logic.VerifyCertificateUseCase
import io.tokend.certificates.feature.info.view.InfoFragment
import io.tokend.certificates.feature.scanQR.view.ScanQrCameraActivity
import io.tokend.certificates.feature.scanQR.view.ScanQrImageFragment
import io.tokend.certificates.feature.verify.logic.parseCertificateUseCase
import io.tokend.certificates.feature.verify.model.CertificateData
import io.tokend.certificates.utils.ObservableTransformers


class HomePageFragment : BaseFragment() {

    private lateinit var binding: FragmentHomePageBinding
    //val isLoading = MutableLiveData(false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)
        binding.lifecycleOwner = this
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        clickHelper.addViews(
            binding.scanCamera,
            binding.scanImage
        )

        clickHelper.setOnClickListener {
            when (it.id) {
                binding.scanImage.id -> {
                    openSelectImage()
                }
                binding.scanCamera.id -> {
                    openCamera()
                }
            }
        }
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val fragment = ScanQrImageFragment.newInstance(uri.toString())
                parentFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

    private fun openSelectImage() {
        pickImage.launch("image/*")
    }


    private fun openCamera() {
        val intent = Intent(context, ScanQrCameraActivity::class.java)
        barcodeLauncher.launch(intent)
    }


    // Register the launcher and result handler
    private val barcodeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {

                    val res = result.data!!.getStringExtra(ScanQrCameraActivity.RESULT_CODE)
                    val certificate: CertificateData
                    try {
                        certificate = parseCertificateUseCase.parse(res!!)
                    } catch (e: Exception) {
                        showScanErrorDialog()
                        return@registerForActivityResult
                    }

                    VerifyCertificateUseCase(apiProvider).verify(certificate)
                        .compose(ObservableTransformers.defaultSchedulersSingle())
                        .subscribe({
                            val infoFragment = InfoFragment.newInstance(certificate, it)
                            parentFragmentManager.beginTransaction()
                                .add(R.id.fragment_container, infoFragment)
                                .addToBackStack(null)
                                .commit()
                        }, {

                            if (it is java.lang.RuntimeException) {
                                toastManager.long(getString(R.string.no_internet_error))
                            } else {
                                it.printStackTrace()
                                showScanErrorDialog()
                            }

                        }).addTo(compositeDisposable)
                }

            }
        }

    private fun showScanErrorDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.scan_error)
            .setMessage(R.string.scan_error_message)
            .setNegativeButton(resources.getString(R.string.back_home)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.try_again)) { dialog, which ->
                openCamera()
            }
            .show()
    }

    companion object {
        fun newInstance(): HomePageFragment {
            return HomePageFragment()
        }
    }

}